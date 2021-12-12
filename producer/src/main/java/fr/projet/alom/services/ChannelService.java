package fr.projet.alom.services;

import fr.projet.alom.clients.AuthClient;
import fr.projet.alom.database.Database;
import fr.projet.alom.entities.*;
import fr.projet.alom.entities.requests.PublicChannelRequest;
import fr.projet.alom.exceptions.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ChannelService {

    private final AuthClient authClient = new AuthClient();

    public Message sendMessage(Message message) throws UnknownHostException, ExecutionException, InterruptedException, TokenInvalid, ChannelNotFound, ChannelNotAccess {
        User user = this.authClient.callCheckToken(message.getTokenSender()).orElseThrow(() -> new TokenInvalid(message.getTokenSender()));
        Channel channel = this.getChannel(message.getChannelName());
        if (!checkAccess(user.getUsername(), channel)) {
            throw new ChannelNotAccess();
        }
        writeInKafka(channel.getNameTopic(), "[" + channel.getNameTopic() +  "] " + user.getUsername() + " - " + message.getMessage());
        return message;
    }

    private boolean writeInKafka(String topicName, String message) throws UnknownHostException, ExecutionException, InterruptedException {
        Properties config = new Properties();
        config.put(ProducerConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        KafkaProducer<Long, String> kafkaProducer = new KafkaProducer<>(config);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        final ProducerRecord<Long, String> record = new ProducerRecord<>(topicName, System.currentTimeMillis(), "(" + simpleDateFormat.format(new Date()) + ") " + message);
        Future<RecordMetadata> future = kafkaProducer.send(record);
        RecordMetadata metadata = future.get();
        kafkaProducer.close();
        return true;
    }

    public Channel getChannel(String topicName) throws ChannelNotFound {
        return Database.getInstance().getChannels().stream()
                .filter(channel -> channel.getNameTopic().equals(topicName))
                .findFirst()
                .orElseThrow(() -> new ChannelNotFound(topicName));
    }

    private boolean checkAccess(String sender, Channel channel) {
        if (channel instanceof PublicChannel) {
            PublicChannel publicChannel = (PublicChannel) channel;
            return publicChannel.getUsers().stream().anyMatch(user -> user.equals(sender));
        } else if (channel instanceof PrivateChannel) {
            PrivateChannel privateChannel = (PrivateChannel) channel;
            return privateChannel.getUser1().equals(sender) || privateChannel.getUser2().equals(sender);
        }
        return false;
    }

    public MessagePrivate sendMessagePrivate(MessagePrivate messagePrivate) throws TokenInvalid, UnknownHostException, ExecutionException, InterruptedException {
        User user = authClient.callCheckToken(messagePrivate.getTokenSender()).orElseThrow(() -> new TokenInvalid(messagePrivate.getTokenSender()));
        Optional<Channel> optionalChannel = Database.getInstance().getChannels().stream().filter(channel ->
                        (channel instanceof PrivateChannel)
                        && ((((PrivateChannel) channel).getUser1().equals(messagePrivate.getReceiver()) && ((PrivateChannel) channel).getUser2().equals(user.getUsername()))
                        || (((PrivateChannel) channel).getUser1().equals(user.getUsername()) && ((PrivateChannel) channel).getUser2().equals(messagePrivate.getReceiver())))
                ).findFirst();
        PrivateChannel channel;
        if (optionalChannel.isPresent()) {
            channel = (PrivateChannel) optionalChannel.get();
        }else{
            channel = new PrivateChannel(user.getUsername() + "-" + messagePrivate.getReceiver(), user.getUsername(), messagePrivate.getReceiver());
            Database.getInstance().getChannels().add(channel);
        }
        writeInKafka(channel.getNameTopic(), user.getUsername() + " - " + messagePrivate.getMessage());
        return messagePrivate;
    }

    public PublicChannel createPublicChannel(PublicChannelRequest publicChannelRequest) throws ChannelAlreadyExist, TokenInvalid, UnknownHostException, ExecutionException, InterruptedException {
        if (Database.getInstance().getChannels().stream().anyMatch(channel -> channel.getNameTopic().equals(publicChannelRequest.getTopicName()))) {
            throw new ChannelAlreadyExist(publicChannelRequest.getTopicName());
        }
        User user = authClient.callCheckToken(publicChannelRequest.getTokenSender()).orElseThrow(() -> new TokenInvalid(publicChannelRequest.getTokenSender()));
        PublicChannel publicChannel = new PublicChannel(publicChannelRequest.getTopicName(), Collections.singletonList(user.getUsername()));
        Database.getInstance().getChannels().add(publicChannel);
        writeInKafka(publicChannelRequest.getTopicName(), "[" + publicChannel.getNameTopic() + "] - " + user.getUsername() + " à créé le channel ");
        return publicChannel;
    }

    public PublicChannel joinPublicChannel(PublicChannelRequest publicChannelRequest) throws ChannelNotFound, TokenInvalid, IsPrivateChannel, ChannelAlreadyJoin {
        Channel channel = getChannel(publicChannelRequest.getTopicName());
        User user = authClient.callCheckToken(publicChannelRequest.getTokenSender()).orElseThrow(() -> new TokenInvalid(publicChannelRequest.getTokenSender()));
        if (channel instanceof PrivateChannel) {
            throw new IsPrivateChannel(publicChannelRequest.getTopicName());
        }
        if( checkAccess(user.getUsername(), channel)) {
            throw new ChannelAlreadyJoin(user.getUsername(), publicChannelRequest.getTopicName());
        }
        PublicChannel publicChannel = (PublicChannel) channel;
        List<String> users = new ArrayList<>(publicChannel.getUsers());
        users.add(user.getUsername());
        publicChannel.setUsers(users);
        return publicChannel;
    }

    public ChannelList getMyChannels(String token) throws TokenInvalid {
        User user = authClient.callCheckToken(token).orElseThrow(() -> new TokenInvalid(token));
        List<String> channels = Database.getInstance().getChannels().stream().filter(channel -> {
            if (channel instanceof PublicChannel) {
                return ((PublicChannel) channel).getUsers().contains(user.getUsername());
            } else if (channel instanceof PrivateChannel) {
                return ((PrivateChannel) channel).getUser1().equals(user.getUsername()) || ((PrivateChannel) channel).getUser2().equals(user.getUsername());
            }
            return false;
        }).map(Channel::getNameTopic).collect(Collectors.toList());
        return new ChannelList(channels);
    }

}

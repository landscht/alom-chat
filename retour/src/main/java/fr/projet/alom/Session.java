package fr.projet.alom;

import fr.projet.alom.clients.AuthClient;
import fr.projet.alom.clients.ChannelClient;
import fr.projet.alom.entities.ChannelList;
import fr.projet.alom.entities.User;
import fr.projet.alom.request.Request;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Session implements Runnable{

    private final Request request;
    private final AuthClient authClient = new AuthClient();
    private final ChannelClient channelClient = new ChannelClient();
    private boolean tokenInvalid = true;
    private User currentUser;

    public Session(Request request) {
        this.request = request;
    }

    @Override
    public void run() {
        System.out.println("Un client c'est connecté");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getSocket().getInputStream()));
            while(tokenInvalid) {
                this.request.getSocket().getOutputStream().write("Entre ton token : ".getBytes(StandardCharsets.UTF_8));
                String token = br.readLine();
                Optional<User> user = authClient.callCheckToken(token);
                if (user.isPresent()) {
                    this.currentUser = user.get();
                    this.tokenInvalid = false;
                }
            }
            this.request.getSocket().getOutputStream().write(("Bonjour " + this.currentUser.getUsername() + "\n").getBytes(StandardCharsets.UTF_8));
            ChannelList channels = channelClient.getChannels(currentUser.getToken());
            System.out.println(channels.getChannels() != null ? channels.getChannels().size() : "aucun");
            runConsumerKafka(this.request.getSocket().getOutputStream(), channels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runConsumerKafka(OutputStream outputStream, ChannelList channelList) throws IOException {
        Properties config = new Properties();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerTest");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<Long, String> kafkaConsumer = new KafkaConsumer<Long, String>(config);
        List<TopicPartition> topicPartitions = new ArrayList<>();
        if (channelList.getChannels() != null) {
            outputStream.write("Voici la liste de vos topics :\n".getBytes(StandardCharsets.UTF_8));
            for(String topicName: channelList.getChannels()) {
                outputStream.write((topicName + "\n").getBytes(StandardCharsets.UTF_8));
                PartitionInfo partitionInfo = kafkaConsumer.listTopics().get(topicName).get(0);
                TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
                topicPartitions.add(topicPartition);
            }
            outputStream.write("--------------------\n".getBytes(StandardCharsets.UTF_8));
            kafkaConsumer.assign(topicPartitions);
            kafkaConsumer.seekToBeginning(topicPartitions);
            while (true) {
                ConsumerRecords<Long, String> records = kafkaConsumer.poll(100);
                for (ConsumerRecord<Long, String> record : records)
                    outputStream.write((record.value() + "\n").getBytes(StandardCharsets.UTF_8));
            }
        }else {
            outputStream.write("Vous n'avez aucun channel\n".getBytes(StandardCharsets.UTF_8));
            outputStream.write("--------------------\n".getBytes(StandardCharsets.UTF_8));
        }
    }
}

package fr.projet.alom.clients;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import fr.projet.alom.configs.Routes;
import fr.projet.alom.entities.Message;
import fr.projet.alom.entities.MessagePrivate;
import fr.projet.alom.entities.PublicChannel;
import fr.projet.alom.entities.requests.PublicChannelRequest;

import javax.ws.rs.core.MediaType;

public class ProducerClient {

    private final WebResource service;

    public ProducerClient() {
        Client client = Client.create(new DefaultClientConfig());
        this.service = client.resource(Routes.PRODUCER);
    }

    public Message sendMessage(Message message) {
        return this.service
                .path("/message")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(Message.class, message);
    }

    public PublicChannel createPublicChannel(PublicChannelRequest publicChannelRequest) {
        return this.service
                .path("/create/publicChannel")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(PublicChannel.class, publicChannelRequest);
    }

    public MessagePrivate sendMessagePrivate(MessagePrivate messagePrivate) {
        return this.service
                .path("/message/private")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(MessagePrivate.class, messagePrivate);
    }

    public PublicChannel joinPublicChannel(PublicChannelRequest publicChannelRequest) {
        return this.service
                .path("/join/publicChannel")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(PublicChannel.class, publicChannelRequest);
    }

}

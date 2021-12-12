package fr.projet.alom.resources;

import fr.projet.alom.clients.ProducerClient;
import fr.projet.alom.entities.Message;
import fr.projet.alom.entities.MessagePrivate;
import fr.projet.alom.entities.PublicChannel;
import fr.projet.alom.entities.requests.PublicChannelRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/message")
public class MessageResource {

    private final ProducerClient producerClient = new ProducerClient();

    @POST
    @Path("/message")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message sendMessage(Message message) {
        return this.producerClient.sendMessage(message);
    }

    @POST
    @Path("/join/publicChannel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PublicChannel joinPublicChannel(PublicChannelRequest publicChannelRequest) {
        return this.producerClient.joinPublicChannel(publicChannelRequest);
    }

    @POST
    @Path("/message/private")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public MessagePrivate sendMessagePrivate(MessagePrivate messagePrivate) {
        return this.producerClient.sendMessagePrivate(messagePrivate);
    }

    @POST
    @Path("/create/publicChannel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PublicChannel createPublicChannel(PublicChannelRequest publicChannelRequest) {
        return this.producerClient.createPublicChannel(publicChannelRequest);
    }

}

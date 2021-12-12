package fr.projet.alom.resources;

import fr.projet.alom.entities.*;
import fr.projet.alom.entities.requests.PublicChannelRequest;
import fr.projet.alom.exceptions.*;
import fr.projet.alom.services.ChannelService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/producer")
public class ChannelResource {

    private final ChannelService channelService = new ChannelService();

    public ChannelResource() throws UnknownHostException {
    }

    @POST
    @Path("/message")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message sendMessage(Message message) throws UnknownHostException, ExecutionException, InterruptedException, TokenInvalid, ChannelNotAccess, ChannelNotFound {
        return channelService.sendMessage(message);
    }

    @POST
    @Path("/join/publicChannel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PublicChannel joinPublicChannel(PublicChannelRequest publicChannelRequest) throws TokenInvalid, IsPrivateChannel, ChannelNotFound, ChannelAlreadyJoin {
        return channelService.joinPublicChannel(publicChannelRequest);
    }

    @POST
    @Path("/message/private")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public MessagePrivate sendMessagePrivate(MessagePrivate messagePrivate) throws TokenInvalid, UnknownHostException, ExecutionException, InterruptedException {
        return channelService.sendMessagePrivate(messagePrivate);
    }

    @POST
    @Path("/create/publicChannel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PublicChannel createPublicChannel(PublicChannelRequest publicChannelRequest) throws TokenInvalid, ChannelAlreadyExist, UnknownHostException, ExecutionException, InterruptedException {
        return channelService.createPublicChannel(publicChannelRequest);
    }

    @GET
    @Path("/channels/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public ChannelList getChannels(@PathParam("token") String token) throws TokenInvalid {
        return channelService.getMyChannels(token);
    }



}

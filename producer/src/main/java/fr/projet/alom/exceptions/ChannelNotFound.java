package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response;

public class ChannelNotFound extends RESTException{

    public ChannelNotFound(String topicName) {
        super(new ErrorMessage(Response.Status.NOT_FOUND,"2", "channel " + topicName + " not found", "not found"));
    }

}

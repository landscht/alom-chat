package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response;

public class ChannelAlreadyExist extends RESTException{

    public ChannelAlreadyExist(String topicName) {
        super(new ErrorMessage(Response.Status.FORBIDDEN,"1", "Channel " + topicName + " already exist", "forbidden"));
    }

}

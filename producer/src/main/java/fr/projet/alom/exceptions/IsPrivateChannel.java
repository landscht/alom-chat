package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response;

public class IsPrivateChannel extends RESTException{

    public IsPrivateChannel(String topicName) {
        super(new ErrorMessage(Response.Status.FORBIDDEN,"3", "Channel " + topicName + " is private", "forbidden"));
    }

}

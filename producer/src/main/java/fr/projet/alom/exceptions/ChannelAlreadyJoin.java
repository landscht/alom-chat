package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response;

public class ChannelAlreadyJoin extends RESTException{

    public ChannelAlreadyJoin(String user, String topicName) {
        super(new ErrorMessage(Response.Status.FORBIDDEN,"1", "User " + user + " already join channel " + topicName, "forbidden"));
    }

}

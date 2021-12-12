package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response;

public class ChannelNotAccess extends RESTException{

    public ChannelNotAccess() {
        super(new ErrorMessage(Response.Status.UNAUTHORIZED,"3", "Channel not access", "UNAUTHORIZED"));
    }

}

package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response;

public class UserBadCredentials extends RESTException{

    public UserBadCredentials() {
        super(new ErrorMessage(Response.Status.FORBIDDEN,"1", "Bad credentials", "forbidden"));
    }

}

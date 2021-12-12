package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response;

public class TokenInvalid extends RESTException{

    public TokenInvalid(String token) {
        super(new ErrorMessage(Response.Status.UNAUTHORIZED,"3", "Token " + token + " invalid", "UNAUTHORIZED"));
    }

}

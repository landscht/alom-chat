package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response;

public class UserNotFoundException extends RESTException {

    public UserNotFoundException(String token) {
        super(new ErrorMessage(Response.Status.NOT_FOUND,"2", "User with " + token + " not found", "not found"));
    }

}

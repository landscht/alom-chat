package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response;

public class UserAlreadyExist extends RESTException {

    public UserAlreadyExist(String username) {
        super(new ErrorMessage(Response.Status.FORBIDDEN,"1", "User " + username + " already exist", "forbidden"));
    }

}

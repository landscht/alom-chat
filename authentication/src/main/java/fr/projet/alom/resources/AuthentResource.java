package fr.projet.alom.resources;

import fr.projet.alom.entities.requests.SignupRequest;
import fr.projet.alom.entities.UserInfo;
import fr.projet.alom.exceptions.UserAlreadyExist;
import fr.projet.alom.exceptions.UserBadCredentials;
import fr.projet.alom.exceptions.UserNotFoundException;
import fr.projet.alom.services.AuthentService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/authent")
public class AuthentResource {

    private final AuthentService authentService = new AuthentService();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserInfo checkUser(String token) throws UserNotFoundException {
        return authentService.checkUser(token);
    }

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String signup(SignupRequest signupRequest) throws UserAlreadyExist {
        return authentService.signup(signupRequest);
    }

    @POST
    @Path("/signin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String signin(SignupRequest signupRequest) throws UserBadCredentials {
        return authentService.signin(signupRequest);
    }
}

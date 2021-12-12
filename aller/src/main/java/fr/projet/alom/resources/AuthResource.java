package fr.projet.alom.resources;

import fr.projet.alom.clients.AuthClient;
import fr.projet.alom.entities.requests.SignupRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth")
public class AuthResource {

    private AuthClient authClient = new AuthClient();

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String signup(SignupRequest signupRequest) {
        return authClient.signup(signupRequest);
    }

    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String signin(SignupRequest signupRequest) {
        return authClient.signin(signupRequest);
    }

}

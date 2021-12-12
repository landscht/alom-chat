package fr.projet.alom.clients;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import fr.projet.alom.configs.Routes;
import fr.projet.alom.entities.requests.SignupRequest;

import javax.ws.rs.core.MediaType;

public class AuthClient {

    private final WebResource service;

    public AuthClient() {
        Client client = Client.create(new DefaultClientConfig());
        this.service = client.resource(Routes.AUTHENT);
    }

    public String signin(SignupRequest signupRequest) {
        return this.service.path("/signin")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(String.class, signupRequest);
    }

    public String signup(SignupRequest signupRequest) {
        return this.service.path("/signup")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(String.class, signupRequest);
    }

}

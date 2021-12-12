package fr.projet.alom.clients;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import fr.projet.alom.config.Routes;
import fr.projet.alom.entities.User;

import javax.ws.rs.core.MediaType;
import java.util.Optional;

public class AuthClient {

    private final WebResource service;

    public AuthClient() {
        Client client = Client.create(new DefaultClientConfig());
        this.service = client.resource(Routes.AUTHENT);
    }

    public Optional<User> callCheckToken(String token) {
        try {
            return Optional.of(this.service
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .post(User.class, token));
        }catch (Exception e) {
            return Optional.empty();
        }
    }

}

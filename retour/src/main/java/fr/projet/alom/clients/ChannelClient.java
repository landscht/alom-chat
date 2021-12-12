package fr.projet.alom.clients;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import fr.projet.alom.config.Routes;
import fr.projet.alom.entities.ChannelList;

import javax.ws.rs.core.MediaType;

public class ChannelClient {

    private WebResource service;

    public ChannelClient() {
        Client client = Client.create(new DefaultClientConfig());
        this.service = client.resource(Routes.CHANNEL);
    }

    public ChannelList getChannels(String token) {
        return this.service
                .path("/channels/" + token)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ChannelList.class);
    }

}

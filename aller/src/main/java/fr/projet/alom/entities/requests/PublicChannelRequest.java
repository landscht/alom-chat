package fr.projet.alom.entities.requests;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PublicChannelRequest {

    private String tokenSender;

    private String topicName;

    public PublicChannelRequest(String tokenSender, String topicName) {
        this.tokenSender = tokenSender;
        this.topicName = topicName;
    }

    public PublicChannelRequest() {
    }


    public String getTokenSender() {
        return tokenSender;
    }

    public void setTokenSender(String tokenSender) {
        this.tokenSender = tokenSender;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}

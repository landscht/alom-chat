package fr.projet.alom.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

    private String message;

    private String channelName;

    private String tokenSender;

    public Message(String message, String channelName, String tokenSender) {
        this.message = message;
        this.channelName = channelName;
        this.tokenSender = tokenSender;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getTokenSender() {
        return tokenSender;
    }

    public void setTokenSender(String tokenSender) {
        this.tokenSender = tokenSender;
    }

}

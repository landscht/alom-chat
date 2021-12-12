package fr.projet.alom.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessagePrivate {

    private String receiver;

    private String message;

    private String tokenSender;

    public MessagePrivate() {
    }

    public MessagePrivate(String receiver, String message, String tokenSender) {
        this.receiver = receiver;
        this.message = message;
        this.tokenSender = tokenSender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTokenSender() {
        return tokenSender;
    }

    public void setTokenSender(String tokenSender) {
        this.tokenSender = tokenSender;
    }
}

package fr.projet.alom.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PrivateChannel extends Channel {

    private String user1;

    private String user2;

    public PrivateChannel(String nameTopic, String user1, String user2) {
        super(nameTopic);
        this.user1 = user1;
        this.user2 = user2;
    }

    public PrivateChannel(String nameTopic) {
        super(nameTopic);
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }
}

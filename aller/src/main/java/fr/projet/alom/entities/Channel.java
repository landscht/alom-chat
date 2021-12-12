package fr.projet.alom.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Channel {

    private String nameTopic;


    public Channel() {
    }

    public Channel(String nameTopic) {
        this.nameTopic = nameTopic;
    }


    public String getNameTopic() {
        return nameTopic;
    }

    public void setNameTopic(String nameTopic) {
        this.nameTopic = nameTopic;
    }
}

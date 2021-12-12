package fr.projet.alom.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class PublicChannel extends Channel{

    private List<String> users;

    public PublicChannel(String nameTopic, List<String> users) {
        super(nameTopic);
        this.users = users;
    }

    public PublicChannel() {}


    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}

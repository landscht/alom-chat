package fr.projet.alom.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class UserInfo {

    private String username;
    private String token;

    public UserInfo(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public UserInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

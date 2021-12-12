package fr.projet.alom.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class User {

    private String username;
    private String password;
    private String token;

    public User(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public User() {
    }

    public User(String username, String token) {
        this.username = username;
        this.token = token;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

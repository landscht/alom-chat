package fr.projet.alom.services;

import fr.projet.alom.database.Database;
import fr.projet.alom.entities.User;
import fr.projet.alom.entities.requests.SignupRequest;
import fr.projet.alom.entities.UserInfo;
import fr.projet.alom.exceptions.UserAlreadyExist;
import fr.projet.alom.exceptions.UserBadCredentials;
import fr.projet.alom.exceptions.UserNotFoundException;

import java.security.SecureRandom;
import java.util.Optional;

public class AuthentService {

    public UserInfo checkUser(String token) throws UserNotFoundException {
        User user = this.getUserByToken(token).orElseThrow(() -> new UserNotFoundException(token));
        return new UserInfo(user.getUsername(), user.getToken());
    }

    public Optional<User> getUserByToken(String token) {
        return Database.getInstance().getUsers().stream().filter(user -> user.getToken().equals(token)).findFirst();
    }

    public String signup(SignupRequest signupRequest) throws UserAlreadyExist {
        if (Database.getInstance().getUsers().stream().anyMatch(user -> user.getUsername().equals(signupRequest.getUsername()))) {
            throw new UserAlreadyExist(signupRequest.getUsername());
        }
        String token = this.generateToken();
        Database.getInstance().getUsers().add(new User(signupRequest.getUsername(), signupRequest.getPassword(), token));
        return token;
    }

    public String signin(SignupRequest signupRequest) throws UserBadCredentials {
        String token = generateToken();
        User userConnected = Database.getInstance().getUsers().stream().filter(user -> {
            return user.getPassword().equals(signupRequest.getPassword()) &&
                    user.getUsername().equals(signupRequest.getUsername());
        }).findFirst().orElseThrow(UserBadCredentials::new);
        userConnected.setToken(token);
        return userConnected.getToken();
    }

    public String generateToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes.toString();
    }

}

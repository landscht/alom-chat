package fr.projet.alom.exceptions;

public class TokenInvalid extends Exception{

    public TokenInvalid(String token) {
        super("Token " + token + " invalid");
    }

}

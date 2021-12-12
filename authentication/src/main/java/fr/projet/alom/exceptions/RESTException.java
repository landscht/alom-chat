package fr.projet.alom.exceptions;

public class RESTException extends Exception {

    private final ErrorMessage errorMessage;

    public RESTException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}

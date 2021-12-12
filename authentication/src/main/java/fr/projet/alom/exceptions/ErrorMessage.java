package fr.projet.alom.exceptions;

import javax.ws.rs.core.Response.Status;

public class ErrorMessage {

    private Status status;
    private String code;
    private String message;
    private String reason;

    public ErrorMessage(Status status, String code, String message, String reason) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.reason = reason;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

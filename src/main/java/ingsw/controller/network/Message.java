package ingsw.controller.network;

import java.io.Serializable;

public class Message implements Serializable {
    public String sender;
    public String message;

    public Message(String username, String message) {
        this.sender = username;
        this.message = message;
    }

}

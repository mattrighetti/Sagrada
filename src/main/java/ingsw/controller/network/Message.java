package ingsw.controller.network;

public class Message {
    public String sender;
    public String message;

    public Message(String username, String message) {
        this.sender = username;
        this.message = message;
    }

}

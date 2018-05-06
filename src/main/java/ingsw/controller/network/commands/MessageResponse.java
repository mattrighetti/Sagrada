package ingsw.controller.network.commands;

import ingsw.controller.network.Message;

public class MessageResponse implements Response {
    public Message message;

    public MessageResponse(Message message) {
        this.message = message;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

package ingsw.controller.network.commands;

import ingsw.controller.network.Message;

public class MessageResponse implements Response {
    public Message message;

    public MessageResponse(Message message) {
        this.message = message;
    }

    /**
     * Method that declares by which this response should be handled
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

package ingsw.controller.network.commands;

public class UseToolCardResponse implements Response {
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

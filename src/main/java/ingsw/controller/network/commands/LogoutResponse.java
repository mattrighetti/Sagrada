package ingsw.controller.network.commands;

public class LogoutResponse implements Response {
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
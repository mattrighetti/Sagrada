package ingsw.controller.network.commands;

public class EndTurnResponse implements Response {


    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

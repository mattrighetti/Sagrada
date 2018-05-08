package ingsw.controller.network.commands;

public class CreateMatchResponse implements Response {


    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

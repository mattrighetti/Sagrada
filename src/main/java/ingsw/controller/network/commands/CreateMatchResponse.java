package ingsw.controller.network.commands;

public class CreateMatchResponse implements Response {
    public String matchName;

    public CreateMatchResponse(String matchName) {
        this.matchName = matchName;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

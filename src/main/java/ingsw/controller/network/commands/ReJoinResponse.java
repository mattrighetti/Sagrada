package ingsw.controller.network.commands;

public class ReJoinResponse implements Response {
    public String matchName;

    public ReJoinResponse(String matchName) {
        this.matchName = matchName;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

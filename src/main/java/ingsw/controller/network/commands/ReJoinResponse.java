package ingsw.controller.network.commands;

public class ReJoinResponse implements Response {
    public String matchName;
    public String username;

    public ReJoinResponse(String matchName, String username) {
        this.matchName = matchName;
        this.username = username;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

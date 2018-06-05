package ingsw.controller.network.commands;

public class ReJoinMatchRequest implements Request {
    public String matchName;

    public ReJoinMatchRequest(String matchName) {
        this.matchName = matchName;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

package ingsw.controller.network.commands;

public class JoinMatchRequest implements Request {
    public String matchName;

    public JoinMatchRequest(String matchName) {
        this.matchName = matchName;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

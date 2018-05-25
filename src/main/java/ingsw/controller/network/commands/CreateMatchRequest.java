package ingsw.controller.network.commands;

public class CreateMatchRequest implements Request {
    public String matchName;

    public CreateMatchRequest(String matchName) {
        this.matchName = matchName;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

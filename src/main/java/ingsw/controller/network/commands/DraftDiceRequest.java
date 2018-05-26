package ingsw.controller.network.commands;

public class DraftDiceRequest implements Request{
    public String username;

    public DraftDiceRequest(String username) {
        this.username = username;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

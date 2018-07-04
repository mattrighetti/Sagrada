package ingsw.controller.network.commands;

public class DisconnectionRequest implements Request {
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

package ingsw.controller.network.commands;

public class Ack implements Request {
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

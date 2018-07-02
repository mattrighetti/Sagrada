package ingsw.controller.network.commands;

public class Ping implements Response, Request {

    @Override
    public void handle(ResponseHandler responseHandler) {
        return;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return null;
    }
}

package ingsw.controller.network.commands;

public class StopListener implements Request {

    @Override
    public Response handle(RequestHandler requestHandler) {
        return new StopListenerResponse();
    }

}

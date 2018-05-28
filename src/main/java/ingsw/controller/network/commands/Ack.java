package ingsw.controller.network.commands;

public class Ack implements Request {

    /**
     * Method that returns a Response after the Request has been properly handled
     * @param requestHandler class that handles the Request
     * @return corresponding Response
     */
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

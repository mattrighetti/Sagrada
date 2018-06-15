package ingsw.controller.network.commands;

public class ReadHistoryRequest implements Request {
    public String matchName;

    public ReadHistoryRequest(String matchName) {
        this.matchName = matchName;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

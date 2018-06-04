package ingsw.controller.network.commands;

public class UseToolCardRequest implements Request {
    public String toolCardName;

    public UseToolCardRequest(String toolCardName) {
        this.toolCardName = toolCardName;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

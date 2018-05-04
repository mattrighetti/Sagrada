package ingsw.controller.network.commands;

public class LoginUserRequest implements Request {
    public final String username;

    public LoginUserRequest(String username) {
        this.username = username;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

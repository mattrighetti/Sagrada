package ingsw.controller.network.commands;

public class LoginUserRequest extends CommandRequest implements Request {

    public LoginUserRequest(String username) {
        super(username);
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

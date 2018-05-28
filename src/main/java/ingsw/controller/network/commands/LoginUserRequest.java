package ingsw.controller.network.commands;

public class LoginUserRequest implements Request {
    public String username;

    public LoginUserRequest(String username) {
        this.username = username;
    }

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

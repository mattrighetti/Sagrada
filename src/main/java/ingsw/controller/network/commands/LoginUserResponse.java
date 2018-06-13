package ingsw.controller.network.commands;

import ingsw.model.User;

public class LoginUserResponse implements Response {
    public User user;

    public LoginUserResponse(User user) {
        this.user = user;
    }

    /**
     * Method that declares by which this response should be handled
     *
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

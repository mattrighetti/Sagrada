package ingsw.controller.network.commands;

import ingsw.model.User;

public class LoginUserResponse implements Response {
    public User user;

    public LoginUserResponse(User user) {
        this.user = user;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

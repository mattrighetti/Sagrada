package ingsw.controller.network.commands;

import ingsw.model.User;

public class LoginUserResponse extends CommandResponse implements Response {
    public User user;

    public LoginUserResponse(User user) {
        super(user.getUsername());
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

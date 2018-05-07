package ingsw.controller.network.commands;

import ingsw.model.User;

public class LoginUserResponse implements Response {
    public User user;
    public int connectedUsers;

    public LoginUserResponse(User user, int connectedUsers) {
        this.user = user;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

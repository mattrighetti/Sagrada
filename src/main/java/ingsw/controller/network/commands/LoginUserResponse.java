package ingsw.controller.network.commands;

import ingsw.model.User;
import ingsw.utilities.DoubleString;

import java.util.List;

public class LoginUserResponse implements Response {
    public User user;
    public int connectedUsers;
    public List<DoubleString> availableMatches;

    public LoginUserResponse(User user, int connectedUsers, List<DoubleString> availableMatches) {
        this.user = user;
        this.connectedUsers = connectedUsers;
        this.availableMatches = availableMatches;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

package ingsw.controller.network.commands;

public interface RequestHandler {

    Response handle(LoginUserRequest loginUserRequest);

}

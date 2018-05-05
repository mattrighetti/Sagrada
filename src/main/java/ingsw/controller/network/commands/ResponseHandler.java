package ingsw.controller.network.commands;

public interface ResponseHandler {

    void handle(LoginUserResponse loginUserResponse);

    void handle(IntegerResponse integerResponse);

}

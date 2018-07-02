package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LoginUserRequestTest {
    private LoginUserRequest loginUserRequest;
    private ingsw.controller.network.rmi.RMIHandler rmiHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        loginUserRequest = new LoginUserRequest("Test");
        rmiHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
    }

    @Test
    void handle() {
        loginUserRequest.handle(rmiHandler);
        Mockito.verify(rmiHandler, Mockito.times(1)).handle(loginUserRequest);
        loginUserRequest.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(loginUserRequest);
    }
}
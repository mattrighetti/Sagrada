package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LogoutRequestTest {
    private LogoutRequest logoutRequest;
    private RMIHandler rmiHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        logoutRequest = new LogoutRequest();
        rmiHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
    }

    @Test
    void handle() {
        logoutRequest.handle(rmiHandler);
        Mockito.verify(rmiHandler, Mockito.times(1)).handle(logoutRequest);
        logoutRequest.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(logoutRequest);
    }
}
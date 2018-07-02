package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FinishedMatchesRequestTest {
    private FinishedMatchesRequest finishedMatchesResponse;
    private RMIHandler rmiHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        finishedMatchesResponse = new FinishedMatchesRequest();
        rmiHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
    }

    @Test
    void handle() {
        finishedMatchesResponse.handle(rmiHandler);
        Mockito.verify(rmiHandler, Mockito.times(1)).handle(finishedMatchesResponse);
        finishedMatchesResponse.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(finishedMatchesResponse);
    }
}
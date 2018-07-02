package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ReadHistoryRequestTest {
    private ReadHistoryRequest readHistoryRequest;
    private ingsw.controller.network.rmi.RMIHandler rmiHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        readHistoryRequest = new ReadHistoryRequest("Test");
        rmiHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
    }

    @Test
    void handle() {
        readHistoryRequest.handle(rmiHandler);
        Mockito.verify(rmiHandler, Mockito.times(1)).handle(readHistoryRequest);
        readHistoryRequest.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(readHistoryRequest);
    }
}
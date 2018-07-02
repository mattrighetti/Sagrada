package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ReJoinMatchRequestTest {
    private ReJoinMatchRequest reJoinMatchRequest;
    private ingsw.controller.network.rmi.RMIHandler rmiHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        reJoinMatchRequest = new ReJoinMatchRequest("Test");
        rmiHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
    }

    @Test
    void handle() {
        reJoinMatchRequest.handle(rmiHandler);
        Mockito.verify(rmiHandler, Mockito.times(1)).handle(reJoinMatchRequest);
        reJoinMatchRequest.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(reJoinMatchRequest);
    }
}
package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import ingsw.model.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PlaceDiceRequestTest {
    private PlaceDiceRequest placeDiceRequest;
    private ingsw.controller.network.rmi.RMIHandler rmiHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        placeDiceRequest = new PlaceDiceRequest(Mockito.mock(Dice.class), 0, 0);
        rmiHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
    }

    @Test
    void handle() {
        placeDiceRequest.handle(rmiHandler);
        Mockito.verify(rmiHandler, Mockito.times(1)).handle(placeDiceRequest);
        placeDiceRequest.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(placeDiceRequest);
    }
}
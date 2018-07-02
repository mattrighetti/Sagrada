package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AckTest {
    private Ack ack;
    private RMIHandler RMIHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        RMIHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
        ack = new Ack();
    }

    @Test
    void handle() {
        ack.handle(RMIHandler);
        Mockito.verify(RMIHandler, Mockito.times(1)).handle(ack);
        ack.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(ack);
    }
}
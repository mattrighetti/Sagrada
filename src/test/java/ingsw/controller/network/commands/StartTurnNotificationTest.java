package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StartTurnNotificationTest {
    private StartTurnNotification startTurnNotification;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        startTurnNotification = new StartTurnNotification(new HashMap<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        startTurnNotification.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(startTurnNotification);
        startTurnNotification.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(startTurnNotification);
    }
}
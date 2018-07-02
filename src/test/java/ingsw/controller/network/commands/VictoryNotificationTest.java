package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VictoryNotificationTest {
    private VictoryNotification victoryNotification;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        victoryNotification = new VictoryNotification(10);
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        victoryNotification.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(victoryNotification);
        victoryNotification.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(victoryNotification);
    }
}
package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LoseNotificationTest {
    private LoseNotification loseNotification;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        loseNotification = new LoseNotification(5);
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        loseNotification.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(loseNotification);
        loseNotification.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(loseNotification);
    }
}
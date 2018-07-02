package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class MoveStatusNotificationTest {
    private MoveStatusNotification moveStatusNotification;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        moveStatusNotification = new MoveStatusNotification(new ArrayList<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        moveStatusNotification.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(moveStatusNotification);
        moveStatusNotification.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(moveStatusNotification);
    }
}
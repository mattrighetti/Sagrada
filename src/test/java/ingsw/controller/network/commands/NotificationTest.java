package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import ingsw.utilities.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationTest {
    private Notification notification;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        notification = new Notification(NotificationType.DRAFT_DICE);
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        notification.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(notification);
        notification.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(notification);
    }
}
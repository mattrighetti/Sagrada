package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class PatternCardNotificationTest {
    private PatternCardNotification patternCardNotification;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        patternCardNotification = new PatternCardNotification(new ArrayList<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        patternCardNotification.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(patternCardNotification);
        patternCardNotification.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(patternCardNotification);
    }
}
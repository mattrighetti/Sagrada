package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AvoidToolCardResponseTest {
    private AvoidToolCardResponse avoidToolCardResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        avoidToolCardResponse = new AvoidToolCardResponse();
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        avoidToolCardResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(avoidToolCardResponse);
        avoidToolCardResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(avoidToolCardResponse);
    }
}
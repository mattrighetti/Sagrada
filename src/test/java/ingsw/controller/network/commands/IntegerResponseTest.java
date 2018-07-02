package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class IntegerResponseTest {
    private IntegerResponse integerResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        integerResponse = new IntegerResponse(3);
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        integerResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(integerResponse);
        integerResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(integerResponse);
    }
}
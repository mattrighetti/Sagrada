package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class CreateMatchResponseTest {
    private CreateMatchResponse createMatchResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        createMatchResponse = new CreateMatchResponse(new ArrayList<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        createMatchResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(createMatchResponse);
        createMatchResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(createMatchResponse);
    }
}
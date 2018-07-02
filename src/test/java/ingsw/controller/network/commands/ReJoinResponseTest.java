package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReJoinResponseTest {
    private ReJoinResponse reJoinResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        reJoinResponse = new ReJoinResponse("Test", "Test");
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        reJoinResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(reJoinResponse);
        reJoinResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(reJoinResponse);
    }
}
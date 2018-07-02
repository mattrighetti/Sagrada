package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JoinedMatchResponseTest {
    private JoinedMatchResponse joinedMatchResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        joinedMatchResponse = new JoinedMatchResponse(true);
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        joinedMatchResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(joinedMatchResponse);
        joinedMatchResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(joinedMatchResponse);
    }
}
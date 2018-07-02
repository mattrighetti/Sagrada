package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class DraftedDiceResponseTest {
    private DraftedDiceResponse draftedDiceResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        draftedDiceResponse = new DraftedDiceResponse(new ArrayList<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        draftedDiceResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(draftedDiceResponse);
        draftedDiceResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(draftedDiceResponse);
    }
}
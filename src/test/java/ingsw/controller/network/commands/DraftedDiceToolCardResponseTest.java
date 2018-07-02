package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class DraftedDiceToolCardResponseTest {
    private DraftedDiceToolCardResponse draftedDiceToolCardResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        draftedDiceToolCardResponse = new DraftedDiceToolCardResponse(new ArrayList<>(), true);
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        draftedDiceToolCardResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(draftedDiceToolCardResponse);
        draftedDiceToolCardResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(draftedDiceToolCardResponse);
    }
}
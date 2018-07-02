package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import ingsw.utilities.ToolCardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UseToolCardResponseTest {
    private UseToolCardResponse useToolCardResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        useToolCardResponse = new UseToolCardResponse(ToolCardType.FLUX_REMOVER);
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        useToolCardResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(useToolCardResponse);
        useToolCardResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(useToolCardResponse);
    }
}
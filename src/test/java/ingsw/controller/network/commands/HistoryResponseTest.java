package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HistoryResponseTest {
    private HistoryResponse historyResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        historyResponse = new HistoryResponse("[]");
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        // TODO test
        historyResponse.handle(rmiController);
        //Mockito.verify(rmiController, Mockito.times(1)).handle(rmiController);
        historyResponse.handle(clientController);
        //Mockito.verify(clientController, Mockito.times(1)).handle(clientController);
    }
}
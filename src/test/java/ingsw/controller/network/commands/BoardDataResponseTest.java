package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;

class BoardDataResponseTest {
    private BoardDataResponse boardDataResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        boardDataResponse = new BoardDataResponse(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new LinkedList<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        boardDataResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(boardDataResponse);
        boardDataResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(boardDataResponse);
    }
}
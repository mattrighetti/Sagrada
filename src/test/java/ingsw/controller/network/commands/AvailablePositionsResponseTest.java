package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ClientController;
import ingsw.controller.network.socket.ServerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AvailablePositionsResponseTest {
    private AvailablePositionsResponse availablePositionsResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        availablePositionsResponse = new AvailablePositionsResponse(new HashMap<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        availablePositionsResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(availablePositionsResponse);
        availablePositionsResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(availablePositionsResponse);
    }
}
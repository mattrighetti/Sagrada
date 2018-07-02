package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import ingsw.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UpdateViewResponseTest {
    private UpdateViewResponse updateViewResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        updateViewResponse = new UpdateViewResponse(Mockito.mock(Player.class), new HashMap<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        updateViewResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(updateViewResponse);
        updateViewResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(updateViewResponse);
    }
}
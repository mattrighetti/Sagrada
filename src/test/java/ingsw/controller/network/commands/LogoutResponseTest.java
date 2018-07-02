package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LogoutResponseTest {
    private LogoutResponse logoutResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        logoutResponse = new LogoutResponse();
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        logoutResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(logoutResponse);
        logoutResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(logoutResponse);
    }
}
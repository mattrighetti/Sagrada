package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TimeOutResponseTest {
    private TimeOutResponse timeOutResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        timeOutResponse = new TimeOutResponse();
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        timeOutResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(timeOutResponse);
        timeOutResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(timeOutResponse);
    }
}
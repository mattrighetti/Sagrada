package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import ingsw.utilities.ToolCardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MoveToolCardRequestTest {
    private MoveToolCardRequest moveToolCardRequest;
    private ingsw.controller.network.rmi.RMIHandler rmiHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        moveToolCardRequest = new MoveToolCardRequest(ToolCardType.FLUX_REMOVER);
        rmiHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
    }

    @Test
    void handle() {
        moveToolCardRequest.handle(rmiHandler);
        Mockito.verify(rmiHandler, Mockito.times(1)).handle(moveToolCardRequest);
        moveToolCardRequest.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(moveToolCardRequest);
    }
}
package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import ingsw.model.cards.patterncard.PatternCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ChosenPatternCardRequestTest {
    private ChosenPatternCardRequest chosenPatternCardRequest;
    private ingsw.controller.network.rmi.RMIHandler rmiHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        chosenPatternCardRequest = new ChosenPatternCardRequest(Mockito.mock(PatternCard.class));
        rmiHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
    }

    @Test
    void handle() {
        chosenPatternCardRequest.handle(rmiHandler);
        Mockito.verify(rmiHandler, Mockito.times(1)).handle(chosenPatternCardRequest);
        chosenPatternCardRequest.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(chosenPatternCardRequest);
    }
}
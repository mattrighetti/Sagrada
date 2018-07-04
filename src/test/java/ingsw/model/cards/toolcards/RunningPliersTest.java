package ingsw.model.cards.toolcards;

import ingsw.controller.network.socket.UserObserver;
import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.Round;
import ingsw.model.cards.patterncard.PatternCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RunningPliersTest {
    private RunningPliers runningPliers;
    private AtomicBoolean toolcardLock;

    @BeforeEach
    void setUp() {
        runningPliers = new RunningPliers();
        toolcardLock = new AtomicBoolean(false);
    }

    @Test
    void toStringTest() {
        assertEquals("RunningPliers", runningPliers.getName());
        assertEquals("ToolCard{'RunningPliers'}", runningPliers.toString());
    }

    @Test
    void onActionTestFailure() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        AtomicBoolean toolCardLock = mock(AtomicBoolean.class);


        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolCardLock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(playerMock.getPlayerUsername()).thenReturn("username");

        when(gameManagerMock.getTurnInRound()).thenReturn(2);

        runningPliers.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        assertFalse(toolCardLock.get());
    }
/*
    @Test
    void onActionTest() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        RunningPliers runningPliersSpy = spy(runningPliers);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        AtomicBoolean toolCardLock = mock(AtomicBoolean.class);


        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolCardLock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(playerMock.getPlayerUsername()).thenReturn("username");
        doNothing().when(runningPliersSpy).waitForToolCardAction(gameManagerMock);

        when(gameManagerMock.getTurnInRound()).thenReturn(2);
        toolCardLock.set(true);

        runningPliersSpy.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(gameManagerMock.getCurrentRound().blockedTurnPlayers, times(1)).add(gameManagerMock.getCurrentRound().getCurrentPlayer().getPlayerUsername());
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer()).decreaseFavorTokens(runningPliersSpy.getPrice());
        verify(gameManagerMock).runningPliersResponse();
        verify(gameManagerMock.getCurrentRound()).toolCardMoveDone();
        assertFalse(toolCardLock.get());
    }*/
}
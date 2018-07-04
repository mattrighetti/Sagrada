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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GrindingStoneTest {
    private GrindingStone grindingStone;
    private AtomicBoolean toolcardLock;

    @BeforeEach
    void setUp() {
        grindingStone = new GrindingStone();
        toolcardLock = new AtomicBoolean(false);
    }

    @Test
    void toStringTest() {
        assertEquals("GrindingStone", grindingStone.getName());
        assertEquals("ToolCard{'GrindingStone'}", grindingStone.toString());
    }

    @Test
    void onActionTest() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        GrindingStone grindingStoneSpy = spy(this.grindingStone);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);

        //general method mock
        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(playerMock.getPlayerUsername()).thenReturn("username");

        //Threads has not to go in wait
        doNothing().when(grindingStoneSpy).waitForToolCardAction(gameManagerMock);

        //Set if (gameManager.getToolcardLock().get()) return true, continue the toolcard method
        toolcardLock.set(true);

        grindingStoneSpy.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(grindingStoneSpy, times(1)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock, times(1)).grindingStoneResponse();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(grindingStoneSpy.getPrice());
        verify(gameManagerMock.getCurrentRound(), times(1)).toolCardMoveDone();
        assertFalse(gameManagerMock.getToolCardLock().get());
    }

    @Test
    void onActionTestFailure() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        GrindingStone grindingStoneSpy = spy(this.grindingStone);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);

        //general method mock
        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(playerMock.getPlayerUsername()).thenReturn("username");

        //Threads has not to go in wait
        doNothing().when(grindingStoneSpy).waitForToolCardAction(gameManagerMock);

        //Set if (gameManager.getToolcardLock().get()) return true, continue the toolcard method
        toolcardLock.set(false);

        grindingStoneSpy.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(grindingStoneSpy, times(1)).waitForToolCardAction(gameManagerMock);
    }
}
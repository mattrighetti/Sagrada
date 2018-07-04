package ingsw.model.cards.toolcards;

import ingsw.controller.network.socket.UserObserver;
import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.Round;
import ingsw.model.cards.patterncard.PatternCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EglomiseBrushTest {
    private EglomiseBrush eglomiseBrush;
    private AtomicBoolean toolcardLock;

    @BeforeEach
    void setUp() {
        eglomiseBrush = new EglomiseBrush();
        toolcardLock = new AtomicBoolean(false);
    }

    @Test
    void toStringTest() {
        assertEquals("EglomiseBrush", eglomiseBrush.getName());
        assertEquals("ToolCard{'EglomiseBrush'}", eglomiseBrush.toString());
    }

    @Test
    void onActionTest() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        EglomiseBrush eglomiseBrushSpy = spy(eglomiseBrush);
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
        doNothing().when(eglomiseBrushSpy).waitForToolCardAction(gameManagerMock);

        //execution method mock
        when(playerMock.getPatternCard().getNoOfDice()).thenReturn(2);

        //Set if (gameManager.getToolcardLock().get()) return true, continue the toolcard method
        toolcardLock.set(true);

        eglomiseBrushSpy.action(gameManagerMock);

        verify(eglomiseBrushSpy, times(1)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock, times(1)).eglomiseBrushResponse();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(eglomiseBrushSpy.getPrice());
        verify(gameManagerMock.getCurrentRound(), times(1)).toolCardMoveDone();
        assertFalse(gameManagerMock.getToolCardLock().get());


    }

    @Test
    void onActionTestEmptyCondition() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        EglomiseBrush eglomiseBrushSpy = spy(eglomiseBrush);
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
        doNothing().when(eglomiseBrushSpy).waitForToolCardAction(gameManagerMock);


        //execution method mock
        when(playerMock.getPatternCard().getNoOfDice()).thenReturn(2);

        //Set if (gameManager.getToolcardLock().get()) return true, continue the toolcard method
        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        toolcardLock.set(false);

        eglomiseBrushSpy.action(gameManagerMock);
        verify(eglomiseBrushSpy, times(1)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();

    }

    @Test
    void onActionTestFailure() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        EglomiseBrush eglomiseBrushSpy = spy(eglomiseBrush);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        Map<String, Boolean[][]> availablePositions = new HashMap<>();

        //general method mock
        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(playerMock.getPlayerUsername()).thenReturn("username");

        //Threads has not to go in wait
        doNothing().when(eglomiseBrushSpy).waitForToolCardAction(gameManagerMock);

        //execution method mock
        when(playerMock.getPatternCard().getNoOfDice()).thenReturn(0);

        eglomiseBrushSpy.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
    }
}
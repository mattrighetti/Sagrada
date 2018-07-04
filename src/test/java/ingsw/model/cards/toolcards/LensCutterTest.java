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

class LensCutterTest {
    private LensCutter lensCutter;
    private AtomicBoolean toolcardLock;

    @BeforeEach
    void setUp() {
        lensCutter = new LensCutter();
        toolcardLock = new AtomicBoolean(false);
    }

    @Test
    void toStringTest() {
        assertEquals("LensCutter", lensCutter.getName());
        assertEquals("ToolCard{'LensCutter'}", lensCutter.toString());
    }

    @Test
    void onActionTestFailure() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);

        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);

        when(gameManagerMock.getNoOfCurrentRound()).thenReturn(1);

        lensCutter.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        assertFalse(toolcardLock.get());
    }

    @Test
    void onActionTest() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        LensCutter lensCutterSpy = spy(lensCutter);
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
        doNothing().when(lensCutterSpy).waitForToolCardAction(gameManagerMock);

        when(gameManagerMock.getNoOfCurrentRound()).thenReturn(2);

        //Set if (gameManager.getToolcardLock().get()) return true, continue the toolcard method
        toolCardLock.set(true);

        lensCutterSpy.action(gameManagerMock);

        //UserObserver and waitForToolcardAction called only one time
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(lensCutterSpy.getPrice());
        verify(gameManagerMock, times(1)).lensCutterResponse();
        verify(lensCutterSpy, times(1)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        assertFalse(gameManagerMock.getToolCardLock().get());
    }

}
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

class GlazingHammerTest {
    private GlazingHammer glazingHammer;
    private AtomicBoolean toolcardLock;


    @BeforeEach
    void setUp() {
        glazingHammer = new GlazingHammer();
        toolcardLock = new AtomicBoolean(false);
    }

    @Test
    void toStringTest() {
        assertEquals("GlazingHammer", glazingHammer.getName());
        assertEquals("ToolCard{'GlazingHammer'}", glazingHammer.toString());
    }

    @Test
    void onActionTest() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        GlazingHammer glazingHammerSpy = spy(glazingHammer);
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
        doNothing().when(glazingHammerSpy).waitForToolCardAction(gameManagerMock);

        //execute the normal toolcard method
        when(gameManagerMock.getTurnInRound()).thenReturn(2);

        glazingHammerSpy.action(gameManagerMock);

        verify(gameManagerMock, times(1)).glazingHammerResponse();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(glazingHammerSpy.getPrice());
        assertFalse(gameManagerMock.getToolCardLock().get());
    }

    @Test
    void onActionTestFailure() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        GlazingHammer glazingHammerSpy = spy(glazingHammer);
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
        doNothing().when(glazingHammerSpy).waitForToolCardAction(gameManagerMock);

        //execute the avoid toolcard
        when(gameManagerMock.getTurnInRound()).thenReturn(1);

        toolcardLock.set(false);

        glazingHammerSpy.action(gameManagerMock);

        verify(gameManagerMock, times(1)).avoidToolCardUse();
        assertFalse(gameManagerMock.getToolCardLock().get());
    }
}
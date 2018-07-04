package ingsw.model.cards.toolcards;

import ingsw.controller.network.socket.UserObserver;
import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.Round;
import ingsw.model.cards.patterncard.PatternCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FluxRemoverTest {
    private FluxRemover fluxRemover;
    private AtomicBoolean toolcardLock;
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    void setUp() {
        fluxRemover = new FluxRemover();
        toolcardLock = new AtomicBoolean(false);
        byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @Test
    void toStringTest() {
        assertEquals("FluxRemover", fluxRemover.getName());
        assertEquals("ToolCard{'FluxRemover'}", fluxRemover.toString());
    }

    @Test
    void onActionTest() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        FluxRemover fluxRemoverSpy = spy(this.fluxRemover);
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
        doNothing().when(fluxRemoverSpy).waitForToolCardAction(gameManagerMock);

        //Set if (gameManager.getToolcardLock().get()) return true, continue the toolcard method
        toolcardLock.set(true);

        fluxRemoverSpy.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(fluxRemoverSpy, times(1)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock, times(1)).fluxRemoverResponse();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(fluxRemover.getPrice());
        verify(gameManagerMock.getCurrentRound(), times(1)).toolCardMoveDone();
        assertFalse(gameManagerMock.getToolCardLock().get());
        assertTrue(byteArrayOutputStream.toString().contains("end FluxRemover"));
    }

    @Test
    void onActionTestFailure() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        FluxRemover fluxRemoverSpy = spy(this.fluxRemover);
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
        doNothing().when(fluxRemoverSpy).waitForToolCardAction(gameManagerMock);

        //Set if (gameManager.getToolcardLock().get()) return true, continue the toolcard method
        toolcardLock.set(false);

        fluxRemoverSpy.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(fluxRemoverSpy, times(1)).waitForToolCardAction(gameManagerMock);
        assertFalse(byteArrayOutputStream.toString().contains("end FluxRemover"));
    }

}
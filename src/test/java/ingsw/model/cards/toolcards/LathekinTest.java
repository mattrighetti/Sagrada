package ingsw.model.cards.toolcards;

import ingsw.controller.Controller;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.Round;
import ingsw.model.User;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.ControllerTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LathekinTest {
    private Lathekin lathekin;
    private GameManager gameManager;
    private AtomicBoolean toolcardLock = new AtomicBoolean(false);
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    void setUp() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        lathekin = new Lathekin();
        User user = new User("username");
        user.attachUserObserver(mock(UserObserver.class));
        List<Player> players = new ArrayList<>();
        Player player = new Player(user);
        gameManager = new GameManager(players, 30, mock(Controller.class), mock(ControllerTimer.class));
        Round round = new Round(gameManager);
        Whitebox.setInternalState(round, "player", player);
        Whitebox.setInternalState(gameManager, "currentRound", round);
    }

    @Test
    void toStringTest() {
        assertEquals("Lathekin", lathekin.getName());
        assertEquals("ToolCard{'Lathekin'}", lathekin.toString());
    }

    @Test
    void onActionTestFailure() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);

        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);

        when(playerMock.getPatternCard().getNoOfDice()).thenReturn(1);

        lathekin.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound(), times(1)).toolCardMoveDone();
    }

    @Test
    void onActionTest1() throws RemoteException, InterruptedException {
        GameManager gameManagerMock = mock(GameManager.class);
        Lathekin lathekinSpy = spy(lathekin);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        Map<String, Boolean[][]> availablePositions = new HashMap<>();
        AtomicBoolean toolCardLock = mock(AtomicBoolean.class);


        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolCardLock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(playerMock.getPlayerUsername()).thenReturn("username");
        doNothing().when(lathekinSpy).waitForToolCardAction(gameManagerMock);

        //Set if (!gameManager.getToolcardLock().get()) return true, ends the toolcard method
        when(gameManagerMock.getToolCardLock()).thenReturn(new AtomicBoolean(false));

        when(playerMock.getPatternCard().getNoOfDice()).thenReturn(2);

        lathekinSpy.action(gameManagerMock);

        //UserObserver and waitForToolcardAction called only one time
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(lathekinSpy, times(1)).waitForToolCardAction(gameManagerMock);
        assertFalse(byteArrayOutputStream.toString().contains("thread is now awake 1"));

    }


    @Test
    void onActionTest2() throws RemoteException, InterruptedException {
        GameManager gameManagerMock = mock(GameManager.class);
        Lathekin lathekinSpy = spy(lathekin);
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
        doNothing().when(lathekinSpy).waitForToolCardAction(gameManagerMock);

        //execution method mock
        when(playerMock.getPatternCard().getNoOfDice()).thenReturn(2);

        //Set if (!gameManager.getToolcardLock().get()) return false, continue the toolcard method
        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        toolcardLock.set(true);

        //A double move was done
        when(gameManagerMock.getdoubleMove()).thenReturn(true);



        lathekinSpy.action(gameManagerMock);


        verify(lathekinSpy, times(1)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(lathekinSpy.getPrice());
        verify(gameManagerMock, times(1)).lathekinResponse();
        assertFalse(gameManagerMock.getToolCardLock().get());
        assertNull(lathekinSpy.getNewGrid());
        assertNull(lathekinSpy.getOldGrid());
        assertTrue(byteArrayOutputStream.toString().contains("Double move done"));
        assertFalse(byteArrayOutputStream.toString().contains("end Lathekin"));


    }

    @Test
    void onActionTest3() throws RemoteException {

        GameManager gameManagerMock = mock(GameManager.class);
        Lathekin lathekinSpy = spy(lathekin);
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
        doNothing().when(lathekinSpy).waitForToolCardAction(gameManagerMock);

        //execution method mock
        when(playerMock.getPatternCard().getNoOfDice()).thenReturn(2);

        //Set if (!gameManager.getToolcardLock().get()) return false, continue the toolcard method
        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        toolcardLock.set(true);

        //A double move was not done
        when(gameManagerMock.getdoubleMove()).thenReturn(false);



        lathekinSpy.action(gameManagerMock);


        verify(lathekinSpy, times(2)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(lathekinSpy.getPrice());
        verify(gameManagerMock, times(1)).lathekinResponse();
        verify(gameManagerMock.getCurrentRound(), times(1)).toolCardMoveDone();
        assertFalse(gameManagerMock.getToolCardLock().get());
        assertNull(lathekinSpy.getNewGrid());
        assertNull(lathekinSpy.getOldGrid());
        assertTrue(byteArrayOutputStream.toString().contains("end Lathekin"));
    }
}
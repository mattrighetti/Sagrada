package ingsw.model.cards.toolcards;

import ingsw.controller.network.socket.UserObserver;
import ingsw.model.*;
import ingsw.model.cards.patterncard.PatternCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TapWheelTest {
    private TapWheel tapWheel;
    private AtomicBoolean toolcardLock;

    @BeforeEach
    void setUp() {
        tapWheel = new TapWheel();
        toolcardLock = new AtomicBoolean(false);
    }

    @Test
    void toStringTest() {
        assertEquals("TapWheel", tapWheel.getName());
        assertEquals("ToolCard{'TapWheel'}", tapWheel.toString());
    }

    @Test
    void onActionTest() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        TapWheel tapWheelSpy = spy(tapWheel);
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
        doNothing().when(tapWheelSpy).waitForToolCardAction(gameManagerMock);

        //execution method mock
        when(playerMock.getPatternCard().isGridEmpty()).thenReturn(false);

        toolcardLock.set(true);

        List<List<Dice>> diceList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<Dice> round = new ArrayList<>();
            round.add(new Dice(Color.BLUE));
            round.add(new Dice(Color.BLUE));
            diceList.add(round);
        }

        when(gameManagerMock.getRoundTrack()).thenReturn(diceList);
        //A double move was not done
        when(gameManagerMock.getdoubleMove()).thenReturn(false);
        when(playerMock.getPatternCard().isGridEmpty()).thenReturn(false);
        when(gameManagerMock.getdoubleMove()).thenReturn(false);

        tapWheelSpy.action(gameManagerMock);


        verify(tapWheelSpy, times(2)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(tapWheelSpy.getPrice());
        assertFalse(gameManagerMock.getToolCardLock().get());
    }

    @Test
    void onActionTestDoubleMove() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        TapWheel tapWheelSpy = spy(tapWheel);
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
        doNothing().when(tapWheelSpy).waitForToolCardAction(gameManagerMock);

        //execution method mock
        when(playerMock.getPatternCard().isGridEmpty()).thenReturn(false);

        toolcardLock.set(true);

        List<List<Dice>> diceList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<Dice> round = new ArrayList<>();
            round.add(new Dice(Color.BLUE));
            round.add(new Dice(Color.BLUE));
            diceList.add(round);
        }

        when(gameManagerMock.getRoundTrack()).thenReturn(diceList);
        //A double move was not done
        when(gameManagerMock.getdoubleMove()).thenReturn(false);
        when(playerMock.getPatternCard().isGridEmpty()).thenReturn(false);
        when(gameManagerMock.getdoubleMove()).thenReturn(true);

        tapWheelSpy.action(gameManagerMock);


        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(tapWheelSpy.getPrice());
        assertFalse(gameManagerMock.getToolCardLock().get());
    }


    @Test
    void onActionTestFailure() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        TapWheel tapWheelSpy = spy(tapWheel);
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
        doNothing().when(tapWheelSpy).waitForToolCardAction(gameManagerMock);

        //execution method mock
        when(playerMock.getPatternCard().isGridEmpty()).thenReturn(false);

        toolcardLock.set(true);

        List<List<Dice>> diceList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<Dice> round = new ArrayList<>();
            diceList.add(round);
        }

        when(gameManagerMock.getRoundTrack()).thenReturn(diceList);
        //A double move was not done
        when(gameManagerMock.getdoubleMove()).thenReturn(false);
        when(playerMock.getPatternCard().isGridEmpty()).thenReturn(false);
        when(gameManagerMock.getdoubleMove()).thenReturn(true);

        tapWheelSpy.action(gameManagerMock);


        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
    }

}
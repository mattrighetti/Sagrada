package ingsw.model.cards.toolcards;

import ingsw.controller.network.socket.UserObserver;
import ingsw.model.*;
import ingsw.model.cards.patterncard.PatternCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GrozingPliersTest {
    private GrozingPliers grozingPliers;
    private AtomicBoolean toolcardLock;

    @BeforeEach
    void setUp() {
        grozingPliers = new GrozingPliers();
        toolcardLock = new AtomicBoolean(false);
    }

    @Test
    void toStringTest() {
        assertEquals("GrozingPliers", grozingPliers.getName());
        assertEquals("ToolCard{'GrozingPliers'}", grozingPliers.toString());
    }

    @Test
    void onActionTestAllOne() throws RemoteException {
        GrozingPliers grozingPliersSpy = spy(grozingPliers);
        GameManager gameManagerMock = mock(GameManager.class);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        List<Dice> draftedDice = new ArrayList<>();
        draftedDice.add(new Dice(1, Color.BLUE));
        draftedDice.add(new Dice(1, Color.BLUE));
        draftedDice.add(new Dice(1, Color.BLUE));

        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        doNothing().when(grozingPliersSpy).waitForToolCardAction(gameManagerMock);

        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        when(gameManagerMock.getDraftedDice()).thenReturn(draftedDice);
        toolcardLock.set(false);

        grozingPliersSpy.action(gameManagerMock);

        boolean allOne = (boolean) Whitebox.getInternalState(grozingPliersSpy, "allOne");
        boolean allSix = (boolean) Whitebox.getInternalState(grozingPliersSpy, "allSix");

        assertTrue(allOne);
        assertFalse(allSix);

        verify(grozingPliersSpy, times(1)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
    }

    @Test
    void onActionTestAllSix() throws RemoteException {
        GrozingPliers grozingPliersSpy = spy(grozingPliers);
        GameManager gameManagerMock = mock(GameManager.class);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        List<Dice> draftedDice = new ArrayList<>();
        draftedDice.add(new Dice(6, Color.BLUE));
        draftedDice.add(new Dice(6, Color.BLUE));
        draftedDice.add(new Dice(6, Color.BLUE));

        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        doNothing().when(grozingPliersSpy).waitForToolCardAction(gameManagerMock);

        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        when(gameManagerMock.getDraftedDice()).thenReturn(draftedDice);
        toolcardLock.set(false);

        grozingPliersSpy.action(gameManagerMock);

        boolean allOne = (boolean) Whitebox.getInternalState(grozingPliersSpy, "allOne");
        boolean allSix = (boolean) Whitebox.getInternalState(grozingPliersSpy, "allSix");

        assertFalse(allOne);
        assertTrue(allSix);

        verify(grozingPliersSpy, times(1)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
    }

    @Test
    void onActionTestMix() throws RemoteException {
        GrozingPliers grozingPliersSpy = spy(grozingPliers);
        GameManager gameManagerMock = mock(GameManager.class);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        List<Dice> draftedDice = new ArrayList<>();
        draftedDice.add(new Dice(6, Color.BLUE));
        draftedDice.add(new Dice(1, Color.BLUE));
        draftedDice.add(new Dice(6, Color.BLUE));

        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        doNothing().when(grozingPliersSpy).waitForToolCardAction(gameManagerMock);

        when(gameManagerMock.getToolCardLock()).thenReturn(toolcardLock);
        when(gameManagerMock.getDraftedDice()).thenReturn(draftedDice);
        toolcardLock.set(true);

        grozingPliersSpy.action(gameManagerMock);

        boolean allOne = (boolean) Whitebox.getInternalState(grozingPliersSpy, "allOne");
        boolean allSix = (boolean) Whitebox.getInternalState(grozingPliersSpy, "allSix");

        assertFalse(allOne);
        assertFalse(allSix);

        verify(grozingPliersSpy, times(1)).waitForToolCardAction(gameManagerMock);
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).getUserObserver();
        verify(gameManagerMock, times(1)).grozingPliersResponse();
        verify(gameManagerMock.getCurrentRound().getCurrentPlayer(), times(1)).decreaseFavorTokens(grozingPliersSpy.getPrice());
        verify(gameManagerMock.getCurrentRound(), times(1)).toolCardMoveDone();
        assertFalse(gameManagerMock.getToolCardLock().get());

    }
}
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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CopperFoilBurnisherTest {
    private CopperFoilBurnisher copperFoilBurnisher;
    private GameManager gameManager;
    private AtomicBoolean toolCardLock;

    @BeforeEach
    void setUp() {
        copperFoilBurnisher = new CopperFoilBurnisher();
        User user = new User("username");
        user.attachUserObserver(mock(UserObserver.class));
        List<Player> list = new ArrayList<>();
        Player player = new Player(user);
        player.setPatternCard(mock(PatternCard.class));
        gameManager = new GameManager(list, 30, mock(Controller.class), mock(ControllerTimer.class));
        Round round = new Round(gameManager);
        toolCardLock = new AtomicBoolean(false);

        Whitebox.setInternalState(round,"player", player);
        Whitebox.setInternalState(gameManager,"currentRound",round);
    }

    @Test
    void toStringTest() {
        assertEquals("CopperFoilBurnisher", copperFoilBurnisher.getName());
        assertEquals("ToolCard{'CopperFoilBurnisher'}", copperFoilBurnisher.toString());
    }


    /*
        Test the else case in the if
     */
    @Test
    void onActionTest() throws RemoteException {
        GameManager gameManagerMock = mock(GameManager.class);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);

        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);

        when(patternCardMock.getNoOfDice()).thenReturn(0);

        copperFoilBurnisher.action(gameManagerMock);

        verify(patternCardMock,times(1)).getNoOfDice();
        verify(gameManagerMock,times(1)).avoidToolCardUse();


    }

    @Test
    void onActionTest2() throws RemoteException, InterruptedException {
        GameManager gameManagerMock = mock(GameManager.class);
        CopperFoilBurnisher copperFoilBurnisherSpy = spy(copperFoilBurnisher);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);

        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolCardLock);
        when(patternCardMock.getNoOfDice()).thenReturn(3);
        doNothing().when(copperFoilBurnisherSpy).waitForToolCardAction(gameManagerMock);

        toolCardLock.set(true);

       copperFoilBurnisherSpy.action(gameManagerMock);





        verify(gameManagerMock.getCurrentRound(),times(1)).toolCardMoveDone();
        assertFalse(gameManagerMock.getToolCardLock().get());
        verify(gameManagerMock,times(0)).avoidToolCardUse();
        verify(gameManagerMock,times(1)).copperFoilBurnisherResponse();
    }


}
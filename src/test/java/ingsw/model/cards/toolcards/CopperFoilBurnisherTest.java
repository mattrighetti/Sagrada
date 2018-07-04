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
    void onActionTest() throws InterruptedException, RemoteException {


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
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        AtomicBoolean toolCardLockMock = mock(AtomicBoolean.class);

        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolCardLockMock);
        when(patternCardMock.getNoOfDice()).thenReturn(3);

        gameManagerMock.getToolCardLock().set(true);

       new Thread(()-> copperFoilBurnisher.action(gameManagerMock)).start();

       Thread.sleep(3000);

       synchronized (gameManagerMock.getToolCardLock()) {
           gameManagerMock.getToolCardLock().notify();
       }


        verify(gameManagerMock.getCurrentRound(),times(1)).toolCardMoveDone();
        assertEquals(false,gameManagerMock.getToolCardLock().get());
        verify(gameManagerMock,times(0)).avoidToolCardUse();
        verify(gameManagerMock,times(1)).copperFoilBurnisherResponse();
    }


}
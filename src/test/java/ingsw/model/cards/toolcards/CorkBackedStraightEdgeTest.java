package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.Notification;
import ingsw.controller.network.commands.Response;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.Dice;
import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.Round;
import ingsw.model.cards.patterncard.PatternCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.DataInput;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CorkBackedStraightEdgeTest {
    CorkBackedStraightEdge corkBackedStraightEdge;

    @BeforeEach
    void setUp() {
        corkBackedStraightEdge = new CorkBackedStraightEdge();
    }

    @Test
    void toStringTest() {
        assertEquals("CorkBackedStraightEdge", corkBackedStraightEdge.getName());
        assertEquals("ToolCard{'CorkBackedStraightEdge'}", corkBackedStraightEdge.toString());
    }

    /*
    @Test
    void onActionTest() throws RemoteException, InterruptedException {

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


        gameManagerMock.getToolCardLock().set(true);

        new Thread(()-> corkBackedStraightEdge.action(gameManagerMock)).start();

        Thread.sleep(10000);

        synchronized (gameManagerMock.getToolCardLock()) {
            gameManagerMock.getToolCardLock().notify();
        }

        verify(gameManagerMock.getCurrentRound(),times(1)).toolCardMoveDone();
        verify(gameManagerMock,times(1)).corkBackedStraightedgeResponse();
        assertEquals(false,gameManagerMock.getToolCardLock().get());

    }
    */
}
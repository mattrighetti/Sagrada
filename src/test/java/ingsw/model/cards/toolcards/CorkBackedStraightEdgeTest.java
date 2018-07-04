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

class CorkBackedStraightEdgeTest {
    private CorkBackedStraightEdge corkBackedStraightEdge;
    private AtomicBoolean toolCardLock;

    @BeforeEach
    void setUp() {
        corkBackedStraightEdge = new CorkBackedStraightEdge();
        toolCardLock = new AtomicBoolean(false);
    }

    @Test
    void toStringTest() {
        assertEquals("CorkBackedStraightEdge", corkBackedStraightEdge.getName());
        assertEquals("ToolCard{'CorkBackedStraightEdge'}", corkBackedStraightEdge.toString());
    }


    @Test
    void onActionTest() throws RemoteException, InterruptedException {
        CorkBackedStraightEdge corkBackedStraightEdgeSpy = spy(corkBackedStraightEdge);
        GameManager gameManagerMock = mock(GameManager.class);
        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);
        PatternCard patternCardMock = mock(PatternCard.class);


        when(gameManagerMock.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(gameManagerMock.getToolCardLock()).thenReturn(toolCardLock);
        doNothing().when(corkBackedStraightEdgeSpy).waitForToolCardAction(gameManagerMock);


        toolCardLock.set(true);

        corkBackedStraightEdgeSpy.action(gameManagerMock);

        verify(gameManagerMock.getCurrentRound(),times(1)).toolCardMoveDone();
        verify(gameManagerMock,times(1)).corkBackedStraightedgeResponse();
        assertFalse(gameManagerMock.getToolCardLock().get());

    }

}
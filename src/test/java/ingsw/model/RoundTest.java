package ingsw.model;

import ingsw.controller.network.commands.Notification;
import ingsw.controller.network.commands.Response;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.cards.toolcards.ToolCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoundTest {

    private GameManager gameManager = mock(GameManager.class);
    private Round round = new Round(gameManager);
    private Player player = mock(Player.class);
    private ToolCard toolCard = mock(ToolCard.class);
    private UserObserver userObserver;


    @BeforeEach
    void setUp() {
        userObserver = new UserObserver() {
            @Override
            public void onJoin(int numberOfConnectedUsers) throws RemoteException {

            }

            @Override
            public void receiveNotification(Notification notification) throws RemoteException {

            }

            @Override
            public void activateTurnNotification(Map<String, Boolean[][]> booleanMapGrid) throws RemoteException {

            }

            @Override
            public void sendResponse(Response response) throws RemoteException {

            }

            @Override
            public void checkIfActive() throws RemoteException {

            }

            @Override
            public void notifyVictory(int score) throws RemoteException {

            }

            @Override
            public void notifyLost(int score) throws RemoteException {

            }
        };
    }


    @Test
    void startForPlayer() throws RemoteException {
        round = Mockito.spy(new Round(gameManager));

        when(player.getUserObserver()).thenReturn(userObserver);

        round.startForPlayer(player);

        assertEquals(player, round.getCurrentPlayer());
        assertFalse(round.hasPlayerEndedTurn().get());

        // verifies the myLongProcess() method was called
        verify(round).run();

    }

    @Test
    void makeMoveDice() throws IOException, InterruptedException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Dice dice = new Dice(Color.BLUE);
        when(gameManager.makeMove(player, dice, 0, 0)).thenReturn(true);
        when(player.getUserObserver()).thenReturn(userObserver);

        Whitebox.setInternalState(round, "gameManager", gameManager);
        Whitebox.setInternalState(round, "player", player);


        AtomicBoolean hasMadeAMove = (AtomicBoolean) Whitebox.getInternalState(round, "hasMadeAMove");
        assertFalse(hasMadeAMove.get());
        System.setOut(new PrintStream(byteArrayOutputStream));
        round.makeMove(dice,0,0);
        assertTrue(byteArrayOutputStream.toString().contains("Move made"));
        System.setOut(new PrintStream(new ByteArrayOutputStream()));





    }

    @Test
    void makeMoveToolCard() throws RemoteException {

        when(toolCard.getPrice()).thenReturn(1);
        when(player.getFavourTokens()).thenReturn(2);
        when(player.getUserObserver()).thenReturn(userObserver);

        round.startForPlayer(player);

        round.makeMove(toolCard);

        verify(toolCard).increasePrice();
        verify(toolCard).action(gameManager);

    }

    @Test
    void makeMoveToolCard1() throws RemoteException {

        when(toolCard.getPrice()).thenReturn(3);
        when(player.getFavourTokens()).thenReturn(2);
        when(player.getUserObserver()).thenReturn(userObserver);


        round.startForPlayer(player);

        round.makeMove(toolCard);

        verify(player, atLeastOnce()).getUserObserver();

    }
}
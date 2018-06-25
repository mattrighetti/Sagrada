package ingsw.model;

import ingsw.controller.Controller;
import ingsw.model.cards.patterncard.*;
import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import ingsw.model.cards.publicoc.*;
import ingsw.model.cards.toolcards.ToolCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameManager gameManager;

    @BeforeEach
    void setUp() throws RemoteException {
        List<Player> players = new ArrayList<>();
        players.add(new Player(new User("a")));
        players.add(new Player(new User("b")));
        players.add(new Player(new User("c")));
        players.add(new Player(new User("d")));
        gameManager = new GameManager(players, 10, new Controller("Match", 10, 10, SagradaGame.get()));
    }

    @Test
    void checkSetUp() throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {

        Field publicObjectiveCards = gameManager.getClass().getDeclaredField("publicObjectiveCards");
        publicObjectiveCards.setAccessible(true);
        List<PublicObjectiveCard> publicObjectiveCardsList = (List<PublicObjectiveCard>) publicObjectiveCards.get(gameManager);
        assertEquals(10, publicObjectiveCardsList.size());
        publicObjectiveCards.setAccessible(false);

        Field toolCards = gameManager.getClass().getDeclaredField("toolCards");
        toolCards.setAccessible(true);
        List<ToolCard> toolCardList = (List<ToolCard>) toolCards.get(gameManager);
        assertEquals(12, toolCardList.size());
        toolCards.setAccessible(false);

        Field patternCards = gameManager.getClass().getDeclaredField("patternCards");
        patternCards.setAccessible(true);
        List<PatternCard> patternCardsList = (List<PatternCard>) patternCards.get(gameManager);
        assertEquals(24, patternCardsList.size());
        patternCards.setAccessible(false);

        Field privateObjectiveCards = gameManager.getClass().getDeclaredField("privateObjectiveCards");
        privateObjectiveCards.setAccessible(true);
        List<PrivateObjectiveCard> privateObjectiveCardsList = (List<PrivateObjectiveCard>) privateObjectiveCards.get(gameManager);
        assertEquals(1, privateObjectiveCardsList.size());
        privateObjectiveCards.setAccessible(false);

        for (Player player : gameManager.getPlayerList()){
            assertEquals(true, !player.getPrivateObjectiveCard().equals(null));
        }
    }

    @Test
    void getPlayerList() {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        for(Player player : gameManager.getPlayerList()) {
            if (player.getUser().getUsername().equals("a"))
                a++;

            if (player.getUser().getUsername().equals("b"))
                b++;

            if (player.getUser().getUsername().equals("c"))
                c++;

            if (player.getUser().getUsername().equals("d"))
                d++;
        }
        assertTrue((a == 1 && b == 1 && c == 1 && d ==1));
        assertEquals(4, gameManager.getPlayerList().size());
    }

    @Test
    void draftDiceFromBoard() throws NoSuchFieldException, IllegalAccessException {
        Field Ack = gameManager.getClass().getDeclaredField("noOfAck");
        Ack.setAccessible(true);
        AtomicInteger noOfAck = (AtomicInteger) Ack.get(gameManager);
        assertEquals((new AtomicInteger(0)).get(), noOfAck.get());
        List<Dice> diceList = new ArrayList<>();
        diceList.add(new Dice(Color.BLUE));

        //TODO Find a way to mock the call of UserObserver interface
//      gameManager.draftDiceFromBoard();
    }

    @Test
    void setPatternCardForPlayer() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field field = gameManager.getClass().getDeclaredField("noOfAck");
        field.setAccessible(true);
        Method resetAck = gameManager.getClass().getDeclaredMethod("resetAck");
        resetAck.setAccessible(true);
        resetAck.invoke(gameManager);
        AtomicInteger old = (AtomicInteger) field.get(gameManager);
        gameManager.setPatternCardForPlayer("a", new Batllo());
        AtomicInteger current = (AtomicInteger) field.get(gameManager);
        assertEquals( "PatternCard{'Batllo'}" , gameManager.getPlayerList().get(0).getPatternCard().toString());
        assertEquals(current.get(), old.getAndIncrement());
    }
/*
    @Test
    void waitForEveryPatternCard() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Field noOfAck = gameManager.getClass().getDeclaredField("noOfAck");
        noOfAck.setAccessible(true);
        Method resetAck = gameManager.getClass().getDeclaredMethod("resetAck");
        resetAck.setAccessible(true);
        resetAck.invoke(gameManager);
        AtomicInteger actual = (AtomicInteger) noOfAck.get(gameManager);
        assertEquals((new AtomicInteger(0)).get(), actual.get());
        gameManager.waitForEveryPatternCard();

        gameManager.setPatternCardForPlayer("a", new Batllo());
        gameManager.setPatternCardForPlayer("c", new Batllo());
        gameManager.setPatternCardForPlayer("b", new Batllo());
        gameManager.setPatternCardForPlayer("d", new Batllo());

        actual = (AtomicInteger) noOfAck.get(gameManager);
        assertEquals((new AtomicInteger(4)).get(), actual.get());

        resetAck.invoke(gameManager);

        actual = (AtomicInteger) noOfAck.get(gameManager);
        assertEquals((new AtomicInteger(0)).get(), actual.get());
    }*/
}
package ingsw.model;

import ingsw.controller.Controller;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.Notification;
import ingsw.controller.network.commands.Response;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.cards.patterncard.*;
import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import ingsw.model.cards.publicoc.*;
import ingsw.model.cards.toolcards.*;
import ingsw.utilities.ControllerTimer;
import ingsw.utilities.MoveStatus;
import ingsw.utilities.PlayerBroadcaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.lang.reflect.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class GameManagerTest {

    private GameManager gameManager;
    private Board board;
    private Round round;
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

        List<Player> players = new ArrayList<>();

        User userA = new User("a");
        userA.attachUserObserver(mock(UserObserver.class));
        players.add(new Player(userA));

        User userB = new User("b");
        userB.attachUserObserver(mock(UserObserver.class));
        players.add(new Player(userB));

        User userC = new User("c");
        userC.attachUserObserver(mock(UserObserver.class));
        players.add(new Player(userC));

        User userD = new User("d");
        userD.attachUserObserver(mock(UserObserver.class));
        players.add(new Player(userD));

        ControllerTimer controllerTimer = new ControllerTimer();

        gameManager = new GameManager(players, 10, new Controller("Match", 10, 10, SagradaGame.get()), controllerTimer);

        List<ToolCard> toolCards = new ArrayList<>();
        toolCards.add(new CopperFoilBurnisher());
        toolCards.add(new CorkBackedStraightEdge());
        toolCards.add(new EglomiseBrush());
        toolCards.add(new FluxRemover());
        toolCards.add(new FluxBrush());
        toolCards.add(new GlazingHammer());
        toolCards.add(new GrozingPliers());
        toolCards.add(new Lathekin());
        toolCards.add(new LensCutter());
        toolCards.add(new RunningPliers());
        toolCards.add(new TapWheel());

        //draftedDice in board
        List<Dice> diceList = new ArrayList<>();
        Dice dice1 = new Dice(Color.BLUE);
        Dice dice2 = new Dice(Color.YELLOW);
        Dice dice3 = new Dice(Color.GREEN);
        Dice dice4 = new Dice(Color.RED);
        Dice dice5 = new Dice(Color.PURPLE);
        dice1.roll();
        dice2.roll();
        dice3.roll();
        dice4.roll();
        dice5.roll();
        diceList.add(dice1);
        diceList.add(dice2);
        diceList.add(dice3);
        diceList.add(dice4);
        diceList.add(dice5);

        List<PublicObjectiveCard> publicObjectiveCards = new ArrayList<>();

        board = new Board(publicObjectiveCards,toolCards);
        board.setDraftedDice(diceList);

        round = new Round(gameManager);
        Whitebox.setInternalState(round,"player",players.get(0));
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

    @Test
    void avoidToolCardUse() throws RemoteException {

        Round roundMock = mock(Round.class);
        Whitebox.setInternalState(gameManager,"currentRound", roundMock);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);

        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);

        gameManager.avoidToolCardUse();

        assertEquals(false,gameManager.getToolCardLock().get());


    }

    @Test
    void glazingHammerMove() {
        Dice dice = board.getDraftedDice().get(0);
        Whitebox.setInternalState(gameManager,"board",board);
        Whitebox.setInternalState(gameManager,"currentRound",round);
        Whitebox.setInternalState(round,"player", gameManager.getPlayerList().get(0));
        gameManager.glazingHammerResponse();

        //TODO

    }

    @Test
    void grozingPliersMove() {
        gameManager.getToolCardLock().set(true);

        int oldValue = board.getDraftedDice().get(0).getFaceUpValue();
        Whitebox.setInternalState(gameManager,"board",board);
        Whitebox.setInternalState(gameManager,"currentRound",round);

        gameManager.grozingPliersMove(board.getDraftedDice().get(0),true);

        if (oldValue != 6)
            assertEquals(board.getDraftedDice().get(0).getFaceUpValue(),oldValue + 1);
        else assertEquals(board.getDraftedDice().get(0).getFaceUpValue(),6);

        oldValue = board.getDraftedDice().get(0).getFaceUpValue();

        gameManager.grozingPliersMove(board.getDraftedDice().get(0),false);

        if(oldValue != 1)
            assertEquals(board.getDraftedDice().get(0).getFaceUpValue(),oldValue - 1);
        else assertEquals(board.getDraftedDice().get(0).getFaceUpValue(),1);



    }

    @Test
    void grozingPliersResponse() throws RemoteException {
        gameManager.getToolCardLock().set(true);
        PlayerBroadcaster playerBroadcaster = mock(PlayerBroadcaster.class);

        Whitebox.setInternalState(gameManager,"playerBroadcaster",playerBroadcaster);
        Whitebox.setInternalState(gameManager,"board",board);
        Whitebox.setInternalState(gameManager,"currentRound",round);
        Whitebox.setInternalState(gameManager.getCurrentRound().getCurrentPlayer(), "patternCard", mock(PatternCard.class));

        gameManager.grozingPliersResponse();
        //todo
    }

    @Test
    void pickPatternCards() {

        List<PatternCard> patternCards = (LinkedList<PatternCard>) Whitebox.getInternalState(gameManager,"patternCards");
        int oldSize = patternCards.size();
        int minus = gameManager.getPlayerList().size() * 4;

        HashMap map = gameManager.pickPatternCards();

        patternCards = (LinkedList<PatternCard>) Whitebox.getInternalState(gameManager, "patternCards");
        assertEquals(oldSize - minus, patternCards.size());
        assertEquals(map.size(),gameManager.getPlayerList().size());

    }

    @Test
    void placeDiceForPlayer() {

        Round roundMock = mock(Round.class);
        Whitebox.setInternalState(gameManager,"board",board);
        Whitebox.setInternalState(gameManager,"currentRound",roundMock);

        gameManager.placeDiceForPlayer(gameManager.getDraftedDice().get(0),1,1);

        verify(gameManager.getCurrentRound(),times(1)).makeMove(gameManager.getDraftedDice().get(0),1,1);

    }

    @Test
    void endTurn() {

        Round roundMock = mock(Round.class);
        Whitebox.setInternalState(gameManager,"currentRound",roundMock);
        Player playerMock = mock(Player.class);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getPlayerUsername()).thenReturn("a");

        gameManager.endTurn("a");

        verify(roundMock).avoidEndTurnNotification(true);


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
    }
    */

    @Test
    void avoidToolCardUseTest() throws RemoteException {
        Player player = mock(Player.class);
        Whitebox.setInternalState(round, "player", player);
        Whitebox.setInternalState(gameManager, "currentRound", round);
        when(player.getUserObserver()).thenReturn(userObserver);
        gameManager.avoidToolCardUse();

        verify(player).getUserObserver();
        assertFalse(gameManager.getToolCardLock().get());

    }

    @Test
    void glazingHammerResponseTest() {
        gameManager = mock(GameManager.class);
        Whitebox.setInternalState(gameManager, "board", board);
        List<MoveStatus> moveStatuses = new ArrayList<>();
        Whitebox.setInternalState(gameManager, "movesHistory", moveStatuses);
     //   doNothing().when(gameManager.addMoveToHistoryAndNotify(mock(MoveStatus.class)));
        gameManager.glazingHammerResponse();
    }

}
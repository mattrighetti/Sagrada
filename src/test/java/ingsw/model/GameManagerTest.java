package ingsw.model;

import ingsw.controller.Controller;

import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.cards.patterncard.*;
import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import ingsw.model.cards.publicoc.*;
import ingsw.model.cards.toolcards.*;
import ingsw.utilities.ControllerTimer;
import ingsw.utilities.MoveStatus;
import ingsw.utilities.PlayerBroadcaster;
import ingsw.utilities.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.lang.reflect.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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
            public void activatePinger() {

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

        players.get(0).setPatternCard(new AuroraeMagnificus());


        gameManager = new GameManager(players, 10, mock(Controller.class), mock(ControllerTimer.class));

        PlayerBroadcaster playerBroadcaster = mock(PlayerBroadcaster.class);
        Whitebox.setInternalState(gameManager,"playerBroadcaster",playerBroadcaster);

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
        Dice dice6 = new Dice(Color.YELLOW);
        dice1.setFaceUpValue(1);
        dice2.setFaceUpValue(6);
        dice3.setFaceUpValue(4);
        dice4.setFaceUpValue(2);
        dice5.setFaceUpValue(5);
        dice6.setFaceUpValue(6);
        diceList.add(dice1);
        diceList.add(dice2);
        diceList.add(dice3);
        diceList.add(dice4);
        diceList.add(dice5);

        List<PublicObjectiveCard> publicObjectiveCards = new ArrayList<>();

        board = new Board(publicObjectiveCards, toolCards);
        board.setDraftedDice(diceList);

        round = new Round(gameManager);
        Whitebox.setInternalState(round, "player", players.get(0));
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

        for (Player player : gameManager.getPlayerList()) {
            assertEquals(true, !player.getPrivateObjectiveCard().equals(null));
        }
    }

    @Test
    void getPlayerList() {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        for (Player player : gameManager.getPlayerList()) {
            if (player.getUser().getUsername().equals("a"))
                a++;

            if (player.getUser().getUsername().equals("b"))
                b++;

            if (player.getUser().getUsername().equals("c"))
                c++;

            if (player.getUser().getUsername().equals("d"))
                d++;
        }
        assertTrue((a == 1 && b == 1 && c == 1 && d == 1));
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
        assertEquals("PatternCard{'Batllo'}", gameManager.getPlayerList().get(0).getPatternCard().toString());
        assertEquals(current.get(), old.getAndIncrement());
    }

    @Test
    void avoidToolCardUse() throws RemoteException {

        Round roundMock = mock(Round.class);
        Whitebox.setInternalState(gameManager, "currentRound", roundMock);
        Player playerMock = mock(Player.class);
        UserObserver userObserverMock = mock(UserObserver.class);

        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getUserObserver()).thenReturn(userObserverMock);

        gameManager.avoidToolCardUse();

        assertEquals(false, gameManager.getToolCardLock().get());

    }

    @Test
    void glazingHammerResponse() {

        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        int oldSize = board.getDraftedDice().size();

        gameManager.glazingHammerResponse();

        assertEquals(oldSize, board.getDraftedDice().size());
    }

    @Test
    void grozingPliersMove() {
        gameManager.getToolCardLock().set(true);

        int oldValue = board.getDraftedDice().get(0).getFaceUpValue();
        Whitebox.setInternalState(gameManager, "board", board);
        Whitebox.setInternalState(gameManager, "currentRound", round);

        gameManager.grozingPliersMove(board.getDraftedDice().get(0), true);

        if (oldValue != 6)
            assertEquals(board.getDraftedDice().get(0).getFaceUpValue(), oldValue + 1);
        else assertEquals(board.getDraftedDice().get(0).getFaceUpValue(), 6);

        oldValue = board.getDraftedDice().get(0).getFaceUpValue();

        gameManager.grozingPliersMove(board.getDraftedDice().get(0), false);

        if (oldValue != 1)
            assertEquals(board.getDraftedDice().get(0).getFaceUpValue(), oldValue - 1);
        else assertEquals(board.getDraftedDice().get(0).getFaceUpValue(), 1);


    }

    @Test
    void grozingPliersResponse() throws RemoteException {
        gameManager.getToolCardLock().set(true);

        PlayerBroadcaster playerBroadcaster = (PlayerBroadcaster) Whitebox.getInternalState(gameManager,"playerBroadcaster");

        Mockito.doNothing().when(playerBroadcaster).broadcastResponseToAll(mock(Response.class));
        gameManager.grozingPliersResponse();

    }

    @Test
    void pickPatternCards() {

        List<PatternCard> patternCards = (LinkedList<PatternCard>) Whitebox.getInternalState(gameManager, "patternCards");
        int oldSize = patternCards.size();
        int minus = gameManager.getPlayerList().size() * 4;

        HashMap map = gameManager.pickPatternCards();

        patternCards = (LinkedList<PatternCard>) Whitebox.getInternalState(gameManager, "patternCards");
        assertEquals(oldSize - minus, patternCards.size());
        assertEquals(map.size(), gameManager.getPlayerList().size());

    }

    @Test
    void placeDiceForPlayer() {

        Round roundMock = mock(Round.class);
        Whitebox.setInternalState(gameManager, "board", board);
        Whitebox.setInternalState(gameManager, "currentRound", roundMock);

        gameManager.placeDiceForPlayer(gameManager.getDraftedDice().get(0), 1, 1);

        verify(gameManager.getCurrentRound(), times(1)).makeMove(gameManager.getDraftedDice().get(0), 1, 1);

    }

    @Test
    void endTurn() {

        Round roundMock = mock(Round.class);
        Whitebox.setInternalState(gameManager, "currentRound", roundMock);
        Player playerMock = mock(Player.class);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getPlayerUsername()).thenReturn("a");

        gameManager.endTurn("a");

        verify(roundMock).avoidEndTurnNotification(true);


    }

    @Test
    void stopTurn() {
        Round roundMock = mock(Round.class);
        Whitebox.setInternalState(gameManager, "currentRound", roundMock);
        Player playerMock = mock(Player.class);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getPlayerUsername()).thenReturn("b");

        AtomicBoolean atomicBoolean = (AtomicBoolean) Whitebox.getInternalState(gameManager, "toolCardLock");
        atomicBoolean.set(true);

        gameManager.stopTurn();

        verify(roundMock).setPlayerEndedTurn(true);

        atomicBoolean = (AtomicBoolean) Whitebox.getInternalState(gameManager, "toolCardLock");

        assertTrue(atomicBoolean.get());
    }

    @Test
    void useToolCard() throws InterruptedException {
        Round roundMock = mock(Round.class);
        Whitebox.setInternalState(gameManager, "currentRound", roundMock);
        Whitebox.setInternalState(gameManager, "board", board);

        gameManager.useToolCard("FluxRemover");

        Thread.sleep(1000);

        assertEquals(true, gameManager.getToolCardLock().get());

        FluxRemover fluxRemover = new FluxRemover();
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("FluxRemover")) {
                fluxRemover = (FluxRemover) toolCard;
            }
        }
        verify(roundMock).makeMove(fluxRemover);

    }

    @Test
    void randomizePatternCards() {

        Map<String, List<PatternCard>> mapPatternCards = new HashMap<>();
        List<PatternCard> patternCards = new ArrayList<>();
        patternCards.add(new AuroraSagradis());
        patternCards.add(new AuroraeMagnificus());
        patternCards.add(new Batllo());
        patternCards.add(new Firelight());
        mapPatternCards.put("a", patternCards);
        mapPatternCards.put("b", patternCards);
        mapPatternCards.put("c", patternCards);
        mapPatternCards.put("d", patternCards);


        gameManager.randomizePatternCards(mapPatternCards);

        AtomicBoolean patternCardChosen = (AtomicBoolean) Whitebox.getInternalState(gameManager, "patternCardsChosen");

        assertTrue(patternCardChosen.get());

        boolean verified = false;
        for (PatternCard patternCard : mapPatternCards.get("a")) {
            if (gameManager.getPlayerList().get(0).getPatternCard().toString().equals(patternCard.toString()))
                verified = true;
        }
        assertTrue(verified);
    }

    @Test
    void receiveAck() {
        AtomicInteger atomicInteger = (AtomicInteger) Whitebox.getInternalState(gameManager, "noOfAck");
        int oldValue = atomicInteger.get();
        gameManager.receiveAck();

        atomicInteger = (AtomicInteger) Whitebox.getInternalState(gameManager, "noOfAck");

        assertEquals(oldValue + 1, atomicInteger.get());
    }

    @Test
    void fluxBrushMove1() {

        Whitebox.setInternalState(gameManager, "currentRound", this.round);

        Whitebox.setInternalState(gameManager, "board", this.board);
        gameManager.getToolCardLock().set(true);


        gameManager.fluxBrushMove(board.getDraftedDice().get(0));

        FluxBrush fluxBrush = new FluxBrush();
        boolean verified = false;
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("FluxBrush")) {
                fluxBrush = (FluxBrush) toolCard;
                verified = true;
            }
        }

        if (verified) assertNotNull(fluxBrush.getTemporaryDraftedDice());

    }

    @Test
    void fluxBrushMove2() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);

        gameManager.getToolCardLock().set(true);

        List<Dice> diceList = new ArrayList<>();
        diceList.add(new Dice(Color.YELLOW));
        diceList.add(new Dice(Color.BLUE));

        FluxBrush fluxBrush = new FluxBrush();
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("FluxBrush")) {
                fluxBrush = (FluxBrush) toolCard;
            }
        }
        fluxBrush.setTemporaryDraftedDice(diceList);

        gameManager.fluxBrushMove(gameManager.getDraftedDice().get(0), 1, 1);

        assertNotNull(round.getCurrentPlayer().getPatternCard().getGrid().get(1).get(1).getDice());
        assertEquals(diceList.size(), board.getDraftedDice().size());
    }

    @Test
    void fluxBrushMove3() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);

        List<Dice> diceList = new ArrayList<>();
        diceList.add(new Dice(Color.YELLOW));
        diceList.add(new Dice(Color.BLUE));

        FluxBrush fluxBrush = new FluxBrush();
        boolean verified = false;
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("FluxBrush")) {
                fluxBrush = (FluxBrush) toolCard;
                verified = true;
            }
        }
        fluxBrush.setTemporaryDraftedDice(diceList);

        gameManager.fluxBrushMove();

        assertEquals(diceList.size(), board.getDraftedDice().size());
        for (int i = 0; i < board.getDraftedDice().size(); i++) {
            assertEquals(board.getDraftedDice().get(i).toString(), diceList.get(i).toString());
        }

    }

    @Test
    void fluxRemoverMove1() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);
        int oldSize = board.getDraftedDice().size();

        gameManager.fluxRemoverMove(board.getDraftedDice().get(0));


        FluxRemover fluxRemover = new FluxRemover();
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("FluxRemover")) {
                fluxRemover = (FluxRemover) toolCard;
            }
        }
        assertNotNull(fluxRemover.getDiceFromBag());
        assertNotNull(fluxRemover.getDraftedDice());
        assertEquals(oldSize, fluxRemover.getDraftedDice().size());
        assertTrue(fluxRemover.getDraftedDice().contains(fluxRemover.getDiceFromBag()));

    }

    @Test
    void fluxRemoverMove2a() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);
        Dice dice = board.getDraftedDice().get(0);

        FluxRemover fluxRemover = new FluxRemover();
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("FluxRemover")) {
                fluxRemover = (FluxRemover) toolCard;
            }
        }
        fluxRemover.setDraftedDice(board.getDraftedDice());

        int oldNumberOfDice = 0;
        int newValue = 2;

        for (Dice die : fluxRemover.getDraftedDice()) {
            if (die.toString().equals(dice.getDiceColor().toString() + newValue))
                oldNumberOfDice++;
        }
        int oldSize = board.getDraftedDice().size();

        gameManager.fluxRemoverMove(dice, newValue);

        int numberOfDice = 0;
        for (Dice die : fluxRemover.getDraftedDice()) {
            if (die.toString().equals(dice.getDiceColor().toString() + newValue))
                numberOfDice++;
        }
        int newSize = board.getDraftedDice().size();

        assertEquals(oldSize,newSize);
        assertEquals(oldNumberOfDice + 1 , numberOfDice);

    }

    @Test
    void fluxRemoverMove2b() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);
        Dice dice = board.getDraftedDice().get(0);

        FluxRemover fluxRemover = new FluxRemover();
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("FluxRemover")) {
                fluxRemover = (FluxRemover) toolCard;
            }
        }
        fluxRemover.setDraftedDice(board.getDraftedDice());

        int oldNumberOfDice = 0;
        int newValue = 1;

        for (Dice die : fluxRemover.getDraftedDice()) {
            if (die.toString().equals(dice.getDiceColor().toString() + newValue))
                oldNumberOfDice++;
        }
        int oldSize = board.getDraftedDice().size();

        gameManager.fluxRemoverMove(dice, newValue);

        int numberOfDice = 0;
        for (Dice die : fluxRemover.getDraftedDice()) {
            if (die.toString().equals(dice.getDiceColor().toString() + newValue))
                numberOfDice++;
        }
        int newSize = board.getDraftedDice().size();

        assertEquals(oldSize,newSize);
        assertEquals(oldNumberOfDice , numberOfDice);

    }

    @Test
    void fluxRemoverMove3() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);

        FluxRemover fluxRemover = new FluxRemover();
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("FluxRemover")) {
                fluxRemover = (FluxRemover) toolCard;
            }
        }
        List<Dice> diceList = new ArrayList<>();
        Dice dice1 = new Dice(Color.YELLOW);
        dice1.roll();
        diceList.add(dice1);
        Dice dice2 = new Dice(Color.PURPLE);
        diceList.add(dice2);
        fluxRemover.setDraftedDice(diceList);
        int oldSize = fluxRemover.getDraftedDice().size();
        fluxRemover.setDiceFromBag(new Dice(Color.BLUE));


        gameManager.fluxRemoverMove(dice1, 3, 2);

        assertEquals(oldSize - 1, board.getDraftedDice().size());
        assertTrue(round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(2).getDice().toString().equals(dice1.toString()));

    }

    @Test
    void fluxRemoverMove() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);

        FluxRemover fluxRemover = new FluxRemover();
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("FluxRemover")) {
                fluxRemover = (FluxRemover) toolCard;
            }
        }
        List<Dice> diceList = new ArrayList<>();
        Dice dice1 = new Dice(Color.YELLOW);
        dice1.roll();
        diceList.add(dice1);
        Dice dice2 = new Dice(Color.PURPLE);
        diceList.add(dice2);
        fluxRemover.setDraftedDice(diceList);
        int oldSize = fluxRemover.getDraftedDice().size();
        fluxRemover.setDiceFromBag(new Dice(Color.BLUE));

        gameManager.fluxRemoverMove();

        assertEquals(oldSize, board.getDraftedDice().size());
        boolean verify = true;
        for (Dice die : board.getDraftedDice()) {
            if (!fluxRemover.getDraftedDice().contains(die)) {
                verify = false;
                break;
            }
        }
        assertTrue(verify);
    }

    @Test
    void grindingStoneMove() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);

        for (int i = 1; i <= 6; i++) {
            board.getDraftedDice().get(0).setFaceUpValue(i);
            gameManager.grindingStoneMove(board.getDraftedDice().get(0));

            switch (i) {
                case 1:
                    assertEquals(6, board.getDraftedDice().get(0).getFaceUpValue());
                    break;
                case 2:
                    assertEquals(5, board.getDraftedDice().get(0).getFaceUpValue());
                    break;
                case 3:
                    assertEquals(4, board.getDraftedDice().get(0).getFaceUpValue());
                    break;
                case 4:
                    assertEquals(3, board.getDraftedDice().get(0).getFaceUpValue());
                    break;
                case 5:
                    assertEquals(2, board.getDraftedDice().get(0).getFaceUpValue());
                    break;
                case 6:
                    assertEquals(1, board.getDraftedDice().get(0).getFaceUpValue());
            }
        }

    }

    @Test
    void copperFoilBurnisherMove() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);
        Dice diceToMove = new Dice(Color.PURPLE);
        diceToMove.setFaceUpValue(2);

        round.getCurrentPlayer().getPatternCard().getGrid().get(1).get(1).insertDice(diceToMove);

        gameManager.copperFoilBurnisherMove(new Tuple(1,1),new Tuple(1,2));

        assertEquals(round.getCurrentPlayer().getPatternCard().getGrid().get(1).get(2).getDice().toString(),diceToMove.toString());
        assertTrue(round.getCurrentPlayer().getPatternCard().getGrid().get(1).get(1).getDice() == null);
    }

    @Test
    void corkBackedStraightedgeMove() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);

        Dice diceToPlace = board.getDraftedDice().get(0);

        gameManager.corkBackedStraightedgeMove(diceToPlace,3,1);

        assertEquals(round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(1).getDice().toString(),diceToPlace.toString());

    }

    @Test
    void lensCutterMove() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        List<Dice> diceList = new ArrayList<>();
        Dice dice1 = new Dice(Color.YELLOW);
        dice1.roll();
        diceList.add(dice1);
        Dice dice2 = new Dice(Color.PURPLE);
        dice2.roll();
        diceList.add(dice2);

        List<List<Dice>> roundTrack = new ArrayList<>();
        roundTrack.add(diceList);
        Whitebox.setInternalState(gameManager,"roundTrack",roundTrack);
        gameManager.getToolCardLock().set(true);

        Dice diceInPool = board.getDraftedDice().get(0);
        int oldSizePool = board.getDraftedDice().size();
        int oldSizeRound = roundTrack.get(0).size();

        gameManager.lensCutterMove(0,dice1.toString(),diceInPool.toString());

        boolean verify = false;
        for (Dice die : board.getDraftedDice()) {
            if (die.toString().equals(dice1.toString())) verify = true;
        }

        assertTrue(verify);

        roundTrack = (List<List<Dice>>) Whitebox.getInternalState(gameManager,"roundTrack");
        verify = false;
        for (Dice die : roundTrack.get(0)) {
            if (diceInPool.toString().equals(die.toString())) verify = true;
        }

        assertTrue(verify);
        assertEquals(oldSizePool,board.getDraftedDice().size());
        assertEquals(oldSizeRound,roundTrack.get(0).size());

    }

    @Test
    void eglomiseBrushMove() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);
        Dice diceToMove = new Dice(Color.PURPLE);
        diceToMove.setFaceUpValue(2);

        round.getCurrentPlayer().getPatternCard().getGrid().get(1).get(1).insertDice(diceToMove);

        gameManager.eglomiseBrushMove(new Tuple(1,1),new Tuple(1,2));

        assertEquals(round.getCurrentPlayer().getPatternCard().getGrid().get(1).get(2).getDice().toString(),diceToMove.toString());
        assertTrue(round.getCurrentPlayer().getPatternCard().getGrid().get(1).get(1).getDice() == null);
    }

    @Test
    void runningPliersMove() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);

        Dice diceToPlace = board.getDraftedDice().get(0);

        gameManager.runningPliersMove(diceToPlace, 3,1);

        assertNotNull(round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(1).getDice());
        assertEquals(round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(1).getDice().toString(),diceToPlace.toString());
    }

    @Test
    void lathekinMove1() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);

        Dice dice = new Dice(Color.GREEN);
        dice.setFaceUpValue(2);

        round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(1).insertDice(dice);

        Lathekin lathekin = new Lathekin();
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("Lathekin")) {
                lathekin = (Lathekin) toolCard;
            }
        }


        //First move
        gameManager.lathekinMove(new Tuple(3,1),new Tuple(3,2),false);

        assertNotNull(lathekin.getOldGrid());

        assertNotNull(round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(1).getDice());
        assertEquals(dice.toString(),round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(1).getDice().toString());
        assertNull(round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(2).getDice());

        assertNull(lathekin.getNewGrid().get(3).get(1).getDice());
        assertNotNull(lathekin.getNewGrid().get(3).get(2).getDice());
        assertEquals(dice.toString(),lathekin.getNewGrid().get(3).get(2).getDice().toString());


        //Second move
        gameManager.lathekinMove(new Tuple(3,2), new Tuple(0,4),false);

        assertNotNull(round.getCurrentPlayer().getPatternCard().getGrid().get(0).get(4).getDice());
        assertEquals(dice.toString(),round.getCurrentPlayer().getPatternCard().getGrid().get(0).get(4).getDice().toString());
        assertNull(round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(2).getDice());

    }

    @Test
    void lathekinMove3() {
        Whitebox.setInternalState(gameManager, "board", this.board);
        Whitebox.setInternalState(gameManager, "currentRound", this.round);
        gameManager.getToolCardLock().set(true);

        Dice dice1 = new Dice(Color.GREEN);
        dice1.setFaceUpValue(2);
        Dice dice2 = new Dice(Color.YELLOW);
        dice2.setFaceUpValue(5);

        round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(1).insertDice(dice1);
        round.getCurrentPlayer().getPatternCard().getGrid().get(3).get(2).insertDice(dice2);


        Lathekin lathekin = new Lathekin();
        for (ToolCard toolCard : board.getToolCards()) {
            if (toolCard.getName().equals("Lathekin")) {
                lathekin = (Lathekin) toolCard;
            }
        }

        gameManager.lathekinMove(new Tuple(3,1),new Tuple(3,2),true);

        assertEquals(dice1.toString(),lathekin.getNewGrid().get(3).get(2).getDice().toString());
        assertEquals(dice2.toString(),lathekin.getNewGrid().get(3).get(1).getDice().toString());



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
    void tapWheelMoveTest1() throws RemoteException {
        GameManager gameManagerSpy = spy(gameManager);
        PatternCard patternCard= new Batllo();
        Dice dice1 = new Dice(3,Color.RED);
        Dice dice2 = new Dice(3,Color.RED);
        Dice dice3 = new Dice(5,Color.GREEN);
        patternCard.getGrid().get(0).get(0).insertDice(dice1);
        patternCard.getGrid().get(0).get(1).insertDice(dice3);
        patternCard.getGrid().get(0).get(2).insertDice(dice2);

        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        UserObserver userObserver = mock(UserObserver.class);
        Whitebox.setInternalState(gameManagerSpy, "currentRound", roundMock);
        when(gameManagerSpy.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(patternCardMock.getGrid()).thenReturn(patternCard.getGrid());
        when(playerMock.getUserObserver()).thenReturn(userObserver);

        gameManagerSpy.getToolCardLock().set(true);

        doNothing().when(gameManagerSpy).addMoveToHistoryAndNotify(mock(MoveStatus.class));
        doNothing().when(userObserver).sendResponse(mock(TapWheelResponse.class));
        Tuple dicePosition = new Tuple(0,0);
        Tuple position = new Tuple(1,0);

        gameManagerSpy.tapWheelMove(new Dice(Color.RED), 0, null,null, false);
        gameManagerSpy.tapWheelMove(null, 1, dicePosition , position, false);

        assertNull(patternCard.getGrid().get(0).get(0).getDice());
        assertEquals(dice1, patternCard.getGrid().get(1).get(0).getDice());

    }

    @Test
    void tapWheelMoveTest2() throws RemoteException {
        GameManager gameManagerSpy = spy(gameManager);
        PatternCard patternCard= new Batllo();
        Dice dice1 = new Dice(3,Color.RED);
        Dice dice2 = new Dice(3,Color.RED);
        Dice dice3 = new Dice(5,Color.GREEN);
        patternCard.getGrid().get(0).get(0).insertDice(dice1);
        patternCard.getGrid().get(0).get(1).insertDice(dice3);
        patternCard.getGrid().get(0).get(2).insertDice(dice2);

        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        UserObserver userObserver = mock(UserObserver.class);
        PlayerBroadcaster playerBroadcasterMock = mock(PlayerBroadcaster.class);
        Whitebox.setInternalState(gameManagerSpy, "currentRound", roundMock);
        Whitebox.setInternalState(gameManagerSpy, "board", board);
        Whitebox.setInternalState(gameManagerSpy, "playerBroadcaster", playerBroadcasterMock);
        when(gameManagerSpy.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(patternCardMock.getGrid()).thenReturn(patternCard.getGrid());
        when(playerMock.getUserObserver()).thenReturn(userObserver);
        gameManagerSpy.getToolCardLock().set(true);

        doNothing().when(gameManagerSpy).addMoveToHistoryAndNotify(mock(MoveStatus.class));
        doNothing().when(playerBroadcasterMock).broadcastResponseToAll(mock(PatternCardToolCardResponse.class));

        Tuple dicePosition = new Tuple(0,0);
        Tuple position = new Tuple(1,0);

        gameManagerSpy.tapWheelMove(null, 2, dicePosition , position, false);

        assertNull(patternCard.getGrid().get(0).get(0).getDice());
        assertEquals(dice1, patternCard.getGrid().get(1).get(0).getDice());

    }

    @Test
    void tapWheelMoveTest3() throws RemoteException {
        GameManager gameManagerSpy = spy(gameManager);
        PatternCard patternCard= new Batllo();
        Dice dice1 = new Dice(3,Color.RED);
        Dice dice2 = new Dice(3,Color.RED);
        Dice dice3 = new Dice(5,Color.GREEN);
        patternCard.getGrid().get(0).get(0).insertDice(dice1);
        patternCard.getGrid().get(0).get(1).insertDice(dice3);
        patternCard.getGrid().get(0).get(2).insertDice(dice2);

        Round roundMock = mock(Round.class);
        Player playerMock = mock(Player.class);
        PatternCard patternCardMock = mock(PatternCard.class);
        UserObserver userObserver = mock(UserObserver.class);
        Whitebox.setInternalState(gameManagerSpy, "board", board);
        Whitebox.setInternalState(gameManagerSpy, "currentRound", roundMock);
        when(gameManagerSpy.getCurrentRound()).thenReturn(roundMock);
        when(roundMock.getCurrentPlayer()).thenReturn(playerMock);
        when(playerMock.getPatternCard()).thenReturn(patternCardMock);
        when(patternCardMock.getGrid()).thenReturn(patternCard.getGrid());
        when(playerMock.getUserObserver()).thenReturn(userObserver);

        gameManagerSpy.getToolCardLock().set(true);

        doNothing().when(gameManagerSpy).addMoveToHistoryAndNotify(mock(MoveStatus.class));
        doNothing().when(userObserver).sendResponse(mock(TapWheelResponse.class));
        Tuple dicePosition = new Tuple(0,0);
        Tuple position = new Tuple(0,2);

        gameManagerSpy.tapWheelMove(null, 1, dicePosition , position, true);

        assertNotNull(patternCard.getGrid().get(0).get(0).getDice());
        assertNotNull(patternCard.getGrid().get(0).get(2).getDice());
        assertEquals(dice1, patternCard.getGrid().get(0).get(2).getDice());
        assertEquals(dice2, patternCard.getGrid().get(0).get(0).getDice());

    }

}
/*
 *
 * Un GameManager per ogni partita
 * Board passata da SagradaGame al momento della scelta o creazione della nuova partita o preesistente
 *
 * Contiene tutti gli oggetti del gioco perché si occupa del setup della Board e della creazione della stessa
 *
 * SINGLETON
 *
 */

package ingsw.model;

import com.google.gson.Gson;
import ingsw.controller.Controller;
import ingsw.controller.network.commands.*;
import ingsw.model.cards.patterncard.*;
import ingsw.model.cards.privateoc.*;
import ingsw.model.cards.publicoc.*;
import ingsw.model.cards.toolcards.*;
import ingsw.utilities.ControllerTimer;
import ingsw.utilities.MoveStatus;
import ingsw.utilities.PlayerBroadcaster;
import ingsw.utilities.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

/**
 * Class that handles the entire game process and modifies the model itself
 */
public class GameManager {
    private Board board;
    private int maxTurnSeconds;
    private Round currentRound;
    private boolean brokenWindow;
    private Controller controller;
    private List<Player> playerList;
    private List<MoveStatus> movesHistory;
    private List<PrivateObjectiveCard> privateObjectiveCards;
    private List<PublicObjectiveCard> publicObjectiveCards;
    private List<ToolCard> toolCards;
    private List<PatternCard> patternCards;
    private List<List<Dice>> roundTrack;
    private final AtomicInteger noOfAck;
    private final AtomicBoolean endRound;
    private final AtomicBoolean stop;
    private final AtomicBoolean doubleMove;
    final AtomicBoolean cancelTimer;
    private final AtomicInteger turnInRound;
    public final AtomicBoolean toolCardLock;
    private Set<Player> disconnectedPlayers;
    private PlayerBroadcaster playerBroadcaster;
    private final AtomicBoolean endOfMatch;
    private final ControllerTimer controllerTimer;
    private Thread toolCardThread;


    /**
     * Creates an instance of GameManager with every object needed by the game itself and initializes its players
     * assigning to each of them a PrivateObjectiveCard and asking them to choose a PatternCard.
     *
     * @param players         players that joined the match
     * @param maxTurnSeconds  max seconds that a user should use to complete a turn
     * @param controllerTimer
     */
    public GameManager(List<Player> players, int maxTurnSeconds, Controller controller, ControllerTimer controllerTimer) {
        brokenWindow = false;
        playerList = players;
        this.controller = controller;
        roundTrack = new ArrayList<>();
        movesHistory = new LinkedList<>();
        stop = new AtomicBoolean(true);
        noOfAck = new AtomicInteger(0);
        endRound = new AtomicBoolean(false);
        doubleMove = new AtomicBoolean(false);
        cancelTimer = new AtomicBoolean(false);
        toolCardLock = new AtomicBoolean(false);
        turnInRound = new AtomicInteger(0);
        disconnectedPlayers = new HashSet<>();
        playerBroadcaster = new PlayerBroadcaster(players);
        endOfMatch = new AtomicBoolean(false);
        this.controllerTimer = controllerTimer;
        this.maxTurnSeconds = maxTurnSeconds * 1000;
        setUpGameManager();
    }

    /**
     * Method that will setup the GameManager.
     * This will populate the GameManager with every component needed for the game
     */
    private synchronized void setUpGameManager() {
        setUpPrivateObjectiveCards();

        for (Player player : playerList) {
            player.setPrivateObjectiveCard(privateObjectiveCards.get(0));
            privateObjectiveCards.remove(0);
        }

        setUpPublicObjectiveCards();
        setUpToolCards();
        setUpPatternCards();
    }

    /**
     * Method that populates the PatternCards in the List
     */
    private void setUpPatternCards() {
        this.patternCards = new LinkedList<>();
        this.patternCards.add(new AuroraeMagnificus());
        this.patternCards.add(new AuroraSagradis());
        this.patternCards.add(new Batllo());
        this.patternCards.add(new Bellesguard());
        this.patternCards.add(new ChromaticSplendor());
        this.patternCards.add(new Comitas());
        this.patternCards.add(new Firelight());
        this.patternCards.add(new Firmitas());
        this.patternCards.add(new FractalDrops());
        this.patternCards.add(new FulgorDelCielo());
        this.patternCards.add(new Gravitas());
        this.patternCards.add(new Industria());
        this.patternCards.add(new KaleidoscopicDream());
        this.patternCards.add(new LuxAstram());
        this.patternCards.add(new LuxMundi());
        this.patternCards.add(new LuzCelestial());
        this.patternCards.add(new RipplesOfLight());
        this.patternCards.add(new ShadowThief());
        this.patternCards.add(new SunCatcher());
        this.patternCards.add(new SunsGlory());
        this.patternCards.add(new SymphonyOfLight());
        this.patternCards.add(new ViaLux());
        this.patternCards.add(new Virtus());
        this.patternCards.add(new WaterOfLife());
        Collections.shuffle(patternCards);
    }

    /**
     * Method that populates the ToolCards in the List
     */
    private void setUpToolCards() {
        toolCards = new LinkedList<>();
        this.toolCards.add(new CopperFoilBurnisher());
        this.toolCards.add(new CorkBackedStraightEdge());
        this.toolCards.add(new EglomiseBrush());
        this.toolCards.add(new FluxBrush());
        this.toolCards.add(new FluxRemover());
        this.toolCards.add(new GlazingHammer());
        this.toolCards.add(new GrindingStone());
        this.toolCards.add(new GrozingPliers());
        this.toolCards.add(new Lathekin());
        this.toolCards.add(new LensCutter());
        this.toolCards.add(new RunningPliers());
        this.toolCards.add(new TapWheel());

    }

    /**
     * Method that populates the PublicObjectiveCards in the List
     */
    private void setUpPublicObjectiveCards() {
        publicObjectiveCards = new LinkedList<>();
        this.publicObjectiveCards.add(new ColorDiagonals());
        this.publicObjectiveCards.add(new ColorVariety());
        this.publicObjectiveCards.add(new ColumnShadeVariety());
        this.publicObjectiveCards.add(new DeepShades());
        this.publicObjectiveCards.add(new ColumnColorVariety());
        this.publicObjectiveCards.add(new LightShades());
        this.publicObjectiveCards.add(new MediumShades());
        this.publicObjectiveCards.add(new RowColorVariety());
        this.publicObjectiveCards.add(new RowShadeVariety());
        this.publicObjectiveCards.add(new ShadeVariety());

    }

    /**
     * Method that populates the PrivateObjectiveCards in the List
     */
    private void setUpPrivateObjectiveCards() {
        privateObjectiveCards = new LinkedList<>();
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.BLUE));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.GREEN));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.RED));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.PURPLE));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.YELLOW));
        Collections.shuffle(privateObjectiveCards);
    }

    /**
     * Method that will randomly pick three ToolCards that will be used throughout the game
     *
     * @return three randomly picked ToolCards
     */
    private List<ToolCard> chooseToolCards() {
        //Collections.shuffle(toolCards);
        //return new ArrayList<>(this.toolCards.subList(0, 3));
        List<ToolCard> toolCardList = new LinkedList<>();
        toolCardList.add(new Lathekin());
        toolCardList.add(new FluxBrush());
        toolCardList.add(new TapWheel());
        return toolCardList;

    }

    /**
     * Method that will randomly pick three PublicObjectiveCards that will be used throughout the game
     *
     * @return three randomly picked PublicObjectiveCards
     */
    private List<PublicObjectiveCard> choosePublicObjectiveCards() {
        Collections.shuffle(publicObjectiveCards);
        return new ArrayList<>(publicObjectiveCards.subList(0, 3));
    }

    /**
     * Method that will distribute four PatternCards to each Player
     */
    public HashMap pickPatternCards() {
        HashMap<String, List<PatternCard>> patternCardToChoose = new HashMap<>();
        for (Player player : playerList) {

            try {
                //Need to create another List: subList is not Serializable
                List<PatternCard> patternCardArrayList = new ArrayList<>(patternCards.subList(0, 4));
                player.getUserObserver().sendResponse(new PatternCardNotification(patternCardArrayList));
                patternCardToChoose.put(player.getPlayerUsername(), patternCardArrayList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 4; i++) {
                patternCards.remove(0);
            }
        }
        return patternCardToChoose;
    }

    public int getNoOfCurrentRound() {
        return roundTrack.size() + 1;
    }


    public List<List<Dice>> getRoundTrack() {
        return roundTrack;
    }

    private void deleteMatch() {
        controller.removeMatch();
    }

    /**
     * Method that checks if every user is connected to the game.
     *
     * @param disconnectedPlayers
     */
    private void checkUserConnection(Set<Player> disconnectedPlayers) {
        for (Player player : playerList) {
            try {

                // If there are at least two active players then...
                if (disconnectedPlayers.size() < playerList.size() - 1) {

                    // If a user was in the disconnectedPlayers' Set and it's now active
                    // He gets removed from the set and the necessary data will be notified to him
                    if (disconnectedPlayers.contains(player) && player.getUser().isActive()) {
                        System.out.println("User: " + player.getPlayerUsername() + " is back online! ---> Sending data");
                        disconnectedPlayers.remove(player);
                        player.getUserObserver().sendResponse(new BoardDataResponse(playerList, publicObjectiveCards, toolCards));

                        sleep(500);
                        player.getUserObserver().sendResponse(new DraftedDiceResponse(board.getDraftedDice()));
                    } else if (!disconnectedPlayers.contains(player) && !player.getUser().isActive()) {
                        System.out.println("User " + player.getPlayerUsername() + " has disconnected, adding it to disconnected Users iterating Player " + player.getPlayerUsername() + " " + disconnectedPlayers.size() + " " + (playerList.size() - 1));
                        disconnectedPlayers.add(player);

                    } else {
                        // Check if the User is disconnected or not
                        // If it's disconnected the catch block will handle the disconnection
                        player.getUserObserver();
                    }

                    // If there's only a user connected then...
                } else {

                    stop.set(true);

                    playerBroadcaster.disableBroadcaster();

                    closeThreads();

                    synchronized (endOfMatch) {
                        endOfMatch.notifyAll();
                    }

                    break;
                }

            } catch (RemoteException e) {
                // If a RMI user disconnects, this code will execute
                System.out.println("RMI User " + player.getPlayerUsername() + " disconnected");
                player.getUser().setActive(false);
                disconnectedPlayers.add(player);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    private void closeThreads() throws InterruptedException {
        while (!endOfMatch.get()) {
            synchronized (cancelTimer) {
                cancelTimer.set(true);
                cancelTimer.notifyAll();
            }

            synchronized (currentRound.hasPlayerEndedTurn()) {
                if (currentRound != null) {
                    currentRound.setPlayerEndedTurn(true);
                    currentRound.hasPlayerEndedTurn().wait(1000);
                }
            }

            synchronized (endRound) {
                endRound.set(true);
                endRound.notifyAll();
            }
        }
    }

    /**
     * Method that will check every two seconds which player is active and those who has disconnected from the game.
     * This method is especially handy for checking RMI users disconnection since when a RMI user disconnects you
     * don't get an immediate Exception.
     */
    private void listenForPlayerDisconnection() {
        stop.set(false);
        new Thread(() -> {
            try {
                do {
                    // PING every 2 seconds
                    sleep(2000);

                    checkUserConnection(disconnectedPlayers);

                } while (!stop.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Method that return the current players as a List
     *
     * @return current Players List
     */
    List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Method that will assign the chosen pattern card to the player
     *
     * @param username    player who chose the pattern card
     * @param patternCard pattern card chosen by the player
     */
    public void setPatternCardForPlayer(String username, PatternCard patternCard) {
        if (noOfAck.get() >= 0) {
            for (Player player : playerList) {
                if (player.getPlayerUsername().equals(username)) {
                    player.setPatternCard(patternCard);
                    receiveAck();
                    synchronized (noOfAck) {
                        noOfAck.notifyAll();
                    }
                }
            }
        }
    }

    /**
     * Method that will return the dice drafted at the beginning of the current round
     *
     * @return dice drafted in the Board at the beginning of the current round
     */
    public List<Dice> getDraftedDice() {
        return board.getDraftedDice();
    }

    /**
     * Method that returns the current round's number
     *
     * @return round number
     */
    public int getTurnInRound() {
        return turnInRound.get();
    }

    /**
     * Method that waits for every users to choose a patternCard
     */
    public void waitForEveryPatternCard(Map<String, List<PatternCard>> patternCardToChoose) {
        new Thread(() -> {
            controllerTimer.startPatternCardTimer(30, this, patternCardToChoose);
            waitAck();

            if (noOfAck.get() == playerList.size()) {
                controllerTimer.cancelTimer();
                resetAck();
                setBoardAndStartMatch();
            }
        }).start();
    }

    /**
     * Method that choose the patter card of the player who didn't choose in time randomly
     * Then it starts the match
     *
     * @param patternCardToChoose
     */
    public void randomizePatternCards(Map<String, List<PatternCard>> patternCardToChoose) {
        for (Player player : playerList) {
            if (player.getPatternCard() == null) {
                Collections.shuffle(patternCardToChoose.get(player.getPlayerUsername()));
                player.setPatternCard(patternCardToChoose.get(player.getPlayerUsername()).get(0));
            }
        }
        setBoardAndStartMatch();
        resetAck();

        synchronized (noOfAck) {
            noOfAck.notifyAll();
        }
    }

    private void setBoardAndStartMatch() {
        BoardDataResponse boardDataResponse = new BoardDataResponse(playerList, choosePublicObjectiveCards(), chooseToolCards());
        playerBroadcaster.broadcastResponseToAll(boardDataResponse);
        this.board = new Board(boardDataResponse.publicObjectiveCards, boardDataResponse.toolCards);
        listenForPlayerDisconnection();
        startMatch();
    }

    /**
     * Method that is used to keep track of how many users chose their pattern card
     */
    private void waitAck() {
        synchronized (noOfAck) {
            while (noOfAck.get() < playerList.size()) {
                try {
                    noOfAck.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method that drafts the dice from the board and sends them to every user view
     */
    public void draftDiceFromBoard() {
        playerBroadcaster.broadcastResponseToAll(board.draftDice(playerList.size()));
        addMoveToHistoryAndNotify(new MoveStatus(playerList.get(0).getPlayerUsername(), "Drafted dice"));
        waitForDiceAck();
    }

    /**
     * Method that stalls the program until every user has received every dice
     */
    private void waitForDiceAck() {
        new Thread(() -> {
            System.out.println("Waiting Dice Ack");
            waitAck();
            resetAck();
            startRound();
        }).start();
    }


    public synchronized void placeDiceForPlayer(Dice dice, int rowIndex, int columnIndex) {
        if (!brokenWindow) {
            for (Dice diceInDraftedDice : board.getDraftedDice()) {
                if (diceInDraftedDice.getDiceColor().equals(dice.getDiceColor())
                        && (diceInDraftedDice.getFaceUpValue() == dice.getFaceUpValue())) {
                    currentRound.makeMove(diceInDraftedDice, rowIndex, columnIndex);
                    break;
                }
            }
        }
    }

    public void endTurn(String currentPlayer) {
        if (currentPlayer.equals(currentRound.getCurrentPlayer().getPlayerUsername())) {

            synchronized (cancelTimer) {
                cancelTimer.set(true);
                cancelTimer.notifyAll();
            }
            stopTurn();
        }
    }

    private void stopTurn() {
        addMoveToHistoryAndNotify(new MoveStatus(currentRound.getCurrentPlayer().getPlayerUsername(), "Ended turn"));
        currentRound.setPlayerEndedTurn(true);

        if (toolCardThread != null && toolCardThread.isAlive()) {
            toolCardLock.set(false);
            wakeUpToolCardThread();
        }
    }

    /**
     * Method that resets the received acks to zero
     */
    private void resetAck() {
        noOfAck.set(0);
    }

    /**
     * Method that increments the acks received
     */
    public synchronized void receiveAck() {
        noOfAck.getAndIncrement();
        synchronized (noOfAck) {
            noOfAck.notifyAll();
        }
    }

    /**
     * Method that alerts the user to draft, this activates a button "Draft" in the view
     *
     * @param player player to be notified
     */
    private void notifyDraftToPlayer(Player player) {
        player.notifyDraft();
    }

    /**
     * Method that opens a thread dedicated to the match
     */
    private void startMatch() {
        new Thread(() -> {

            try {
                sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            int i = 0;
            while (i < 10) {
                if (disconnectedPlayers.size() != (playerList.size() - 1)) {
                    if (playerList.get(0).getUser().isActive()) {
                        notifyDraftToPlayer(playerList.get(0));
                        endRound.set(false);

                        System.out.println("Round " + i);
                        //wait until the end of the round
                        synchronized (endRound) {
                            while (!endRound.get()) {
                                try {
                                    endRound.wait();
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        shiftPlayerList();
                    }
                }
                System.out.println("End of Round " + i);
                i++;
            }

            System.out.println("End of Game");

            endOfMatch.set(true);

            synchronized (endOfMatch) {
                try {
                    endOfMatch.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            assignPointsToPlayers();

            notifyWinner();

            writeHistoryToFile(movesHistory);

            stop.set(true);

            deleteMatch();

        }).start();
    }

    private void writeHistoryToFile(List<MoveStatus> movesHistory) {
        Gson gson = new Gson();
        String moveHistoryJSON = gson.toJson(movesHistory);

        try (FileWriter file = new FileWriter("src/main/resources/history/" + playerList.toString() + ".txt")) {
            file.write(moveHistoryJSON);
        } catch (IOException e) {
            System.err.println("There was an error writing the file! Could not complete.");
        }
    }

    private void notifyWinner() {
        Player winner = evaluateWinner();

        for (Player player : playerList) {
            if (winner != null && winner.equals(player) && winner.getUser().isActive()) {
                try {
                    player.getUser().incrementNoOfWins();
                    player.getUserObserver().notifyVictory(player.getScore());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    player.getUser().incrementNoOfLose();
                    player.getUserObserver().notifyLost(player.getScore());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void assignPointsToPlayers() {
        for (Player player : playerList) {
            int score = 0;
            for (PublicObjectiveCard publicObjectiveCard : publicObjectiveCards) {
                score += publicObjectiveCard.getScore(player.getPatternCard().getGrid());
            }
            score -= player.getPatternCard().getNoOfEmptyBoxes();
            player.setScore(score);
        }
    }

    private Set<Player> evaluateBasicPoints() {
        Set<Player> tiePlayers = new HashSet<>();
        int maxScore = -30;
        Player winner = null;

        for (Player player : playerList) {
            if (maxScore < player.getScore()) {
                tiePlayers.clear();
                winner = player;
                maxScore = player.getScore();
            } else if (maxScore == player.getScore()) {
                if (!tiePlayers.contains(winner)) {
                    tiePlayers.add(winner);
                }
                tiePlayers.add(player);
                winner = player;
            }
        }

        if (!tiePlayers.contains(winner)) {
            tiePlayers.add(winner);
        }

        return tiePlayers;
    }

    private Set<Player> evaluatePrivateObjectiveCardPoints(Set<Player> tiePlayers) {
        int privateOc = 0;
        Set<Player> tieAgainPlayers = new HashSet<>();
        Player winner = null;

        for (Player player : tiePlayers) {
            if (privateOc < player.getPrivateObjectiveCard().check(player.getPatternCard().getGrid())) {
                tieAgainPlayers.clear();
                winner = player;
                privateOc = player.getPrivateObjectiveCard().check(player.getPatternCard().getGrid());
            } else if (privateOc == player.getPrivateObjectiveCard().check(player.getPatternCard().getGrid())) {
                if (!tieAgainPlayers.contains(winner)) {
                    tieAgainPlayers.add(winner);
                }
                tieAgainPlayers.add(player);
                winner = player;
            }
        }

        if (!tieAgainPlayers.contains(winner)) {
            tieAgainPlayers.add(winner);
        }

        return tieAgainPlayers;
    }

    private Set<Player> evaluateFavourTokenPoints(Set<Player> tieAgainPlayers) {
        int favorTokens = 0;
        Player winner = null;
        Set<Player> tiePlayers = new HashSet<>();

        for (Player player : tieAgainPlayers) {
            if (favorTokens < player.getFavourTokens()) {
                winner = player;
                tiePlayers.clear();
                favorTokens = player.getFavourTokens();
            } else if (favorTokens == player.getFavourTokens()) {
                if (!tiePlayers.contains(winner)) {
                    tiePlayers.add(winner);
                }
                tiePlayers.add(player);
                winner = player;
            }
        }

        if (!tiePlayers.contains(winner)) {
            tiePlayers.add(winner);
        }

        return tiePlayers;
    }

    /**
     * Method that computes the winner and notifies each player with their results
     */
    private Player evaluateWinner() {
        Player winner = null;
        boolean found = false;
        Set<Player> possibleWinners = evaluateBasicPoints();
        int activeUsers = 0;

        for (Player player : playerList) {
            if (player.getUser().isActive()) {
                activeUsers++;
                winner = player;
            }
        }

        if (activeUsers > 1) {
            winner = null;
            if (possibleWinners.size() > 1) {
                possibleWinners = evaluatePrivateObjectiveCardPoints(possibleWinners);
            } else found = true;

            if (possibleWinners.size() > 1) {
                possibleWinners = evaluateFavourTokenPoints(possibleWinners);
            } else found = true;

            if (possibleWinners.size() > 1) {
                for (Player player : playerList) {
                    if (possibleWinners.contains(player)) {
                        winner = player;
                        found = true;
                        break;
                    }
                }
            } else found = true;

            if (!found) {
                for (Player player : possibleWinners) {
                    winner = player;
                }
            }
        } else {
            if (winner != null) {
                winner.setScore(0);
            }
        }

        return winner;

    }

    /**
     * Method that starts a single round
     */
    private void startRound() {
        currentRound = new Round(this);
        //Rounds going forward
        turnInRound.set(1);

        for (int i = 0; i < playerList.size(); i++) {
            executeTurn(i, "Turn forward ");
        }
        turnInRound.set(2);

        for (int i = playerList.size() - 1; i >= 0; i--) {
            executeTurn(i, "Turn backward ");
        }

        System.out.println("End of turn gm");

        if (!board.getDraftedDice().isEmpty()) {
            roundTrack.add(board.getDraftedDice());
            notifyUpdatedRoundTrack();
        }

        shiftPlayerList();

        //wake up the match thread
        synchronized (endRound) {
            endRound.notifyAll();
        }
    }

    private void executeTurn(int playerIndex, String turnState) {
        System.out.println(turnState + playerIndex + " player " + playerList.get(playerIndex).getPlayerUsername());

        currentRound.setPlayerEndedTurn(false);

        if (playerList.get(playerIndex).getUser().isActive() && (disconnectedPlayers.size() != (playerList.size() - 1))) {
            currentRound.startForPlayer(playerList.get(playerIndex));
            startTimer(maxTurnSeconds);

            //wait until turn has ended
            waitEndTurn();
        }
    }

    private void shiftPlayerList() {
        Player tmp = playerList.get(0);
        playerList.remove(0);
        playerList.add(tmp);
        endRound.set(true);
    }

    private synchronized void startTimer(long time) {
        cancelTimer.set(false);
        new Thread(() -> {
            System.out.println("start timer\n");

            synchronized (cancelTimer) {
                try {
                    cancelTimer.wait(time);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }

            if (!cancelTimer.get()) {

                try {
                    if (toolCardLock.get())
                        currentRound.getCurrentPlayer().getUserObserver().sendResponse(new TimeOutResponse(getDraftedDice(), getRoundTrack(), getCurrentRound().getCurrentPlayer()));
                    else currentRound.getCurrentPlayer().getUserObserver().sendResponse(new TimeOutResponse());

                    synchronized (this) {
                        wait(500);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                cancelTimer.set(false);
                System.out.println("timer ended\n");
                stopTurn();
            }
            System.out.println("Deleting timer");
        }).start();
    }

    private void notifyUpdatedRoundTrack() {
        int round = 0;
        if (!roundTrack.isEmpty()) round = roundTrack.size() - 1;
        playerBroadcaster.broadcastResponseToAll(new RoundTrackNotification(roundTrack.get(round)));
    }

    private void waitEndTurn() {
        synchronized (currentRound.hasPlayerEndedTurn()) {
            while (!currentRound.hasPlayerEndedTurn().get()) {
                try {
                    currentRound.hasPlayerEndedTurn().wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method that notifies the player with a patternCard's mask which indicates the available positions in which
     * a dice can be placed
     *
     * @param player player who's playing at the current time
     */
    Map<String, Boolean[][]> sendAvailablePositions(Player player) {
        return player.getPatternCard().computeAvailablePositionsDraftedDice(board.getDraftedDice());
    }

    /*
     *
     *
     * GAME MOVES
     *
     *
     */

    synchronized boolean makeMove(Player player, Dice dice, int rowIndex, int columnIndex) {
        if (player.getPatternCard().getGrid().get(rowIndex).get(columnIndex).getDice() == null) {


            player.getPatternCard().getGrid().get(rowIndex).get(columnIndex).insertDice(dice);
            board.getDraftedDice().remove(dice);

            // Send updated draftedDice
            playerBroadcaster.broadcastResponseToAll(board.getDraftedDice());

            // Update MovesHistory
            addMoveToHistoryAndNotify(new MoveStatus(player.getPlayerUsername(),
                                                     "Placed dice" + dice + " in [" + rowIndex + ", " + columnIndex + "]"));

            // UpdateView response
            playerBroadcaster.broadcastResponseToAll(new UpdateViewResponse(player, sendAvailablePositions(getCurrentRound().getCurrentPlayer())));
            return true;
        } else
            return false;
    }

    /**
     * Method called when an user selected a patternCard from the view
     *
     * @param toolCardName name of the ToolCard to use
     */
    public void useToolCard(String toolCardName) {
        toolCardThread = new Thread(
                () -> {
                    toolCardLock.set(true);
                    for (ToolCard toolCard : toolCards) {
                        if (toolCard.getName().equals(toolCardName)) {
                            addMoveToHistoryAndNotify(new MoveStatus("Get the name", "Used toolcard " + toolCardName));
                            currentRound.makeMove(toolCard);

                        }
                    }
                });
        toolCardThread.start();
    }

    /**
     * Method that will update the current Moves made by each Player in every User's View
     *
     * @param moveStatus move to be added in the List of Moves made
     */
    private void addMoveToHistoryAndNotify(MoveStatus moveStatus) {
        movesHistory.add(moveStatus);
        playerBroadcaster.updateMovesHistory(movesHistory);
    }

    /**
     * @return the current available Round
     */
    public Round getCurrentRound() {
        return currentRound;
    }


    /*

    -------------------------  TOOLCARDS METHODS  --------------------------------------

     */

    /**
     * Place Dice for Tool Cards
     * <p>
     * Method that place a die in the pattern card for the tool cards that implement this move
     * Find the current player and insert the die in the patterncard, if it is possible, then remove the die from draftedDice
     *
     * @param dice        die to place in Pattern Card
     * @param rowIndex    row index of the position in Pattern Card
     * @param columnIndex column index of the position in Pattern Card
     */
    private synchronized void placeDiceToolCard(Dice dice, int rowIndex, int columnIndex) {
        Player player = getCurrentRound().getCurrentPlayer();
        if (player.getPatternCard().getGrid().get(rowIndex).get(columnIndex).getDice() == null) {
            player.getPatternCard().getGrid().get(rowIndex).get(columnIndex).insertDice(dice);

            for (Dice diceToRemove : getDraftedDice()) {
                if (diceToRemove.toString().equals(dice.toString())) {
                    dice = diceToRemove;
                }
            }

            getDraftedDice().remove(dice);
        }
        synchronized (toolCardLock) {
            toolCardLock.notifyAll();
        }
    }

    /**
     * Avoid ToolCard Use
     * <p>
     * Notify the player with an AvoidToolCardResponse that he cannot use the selected tool card
     */
    public void avoidToolCardUse() {
        try {
            currentRound.getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        toolCardLock.set(false);
    }

    /**
     * Wake Up ToolCard Response
     * <p>
     * Unlock the tool card thread in wait to finish the tool card move
     */
    private void wakeUpToolCardThread() {
        synchronized (toolCardLock) {
            toolCardLock.notifyAll();
        }
    }

    /**
     * Glazing Hammer
     * Method that roll again the drafted dice and send the new drafted pool to all the players
     */
    public synchronized void glazingHammerResponse() {
        for (Dice dice : board.getDraftedDice()) {
            dice.roll();
        }
        boolean endTurnCheck = false;
        if (currentRound.getNoOfMoves() == 0) endTurnCheck = true;
        playerBroadcaster.broadcastResponseToAll(new DraftedDiceToolCardResponse(board.getDraftedDice(), endTurnCheck));
    }

    /**
     * GROZING PLIERS: Move Method
     * <p>
     * Tool card that increase or decrease by one the value of a selected dice from the drafted dice pool:
     * Find the selected dice from the draftedDice and call the method for increasing or decreasing the die's value, depending on increase boolean parameter
     *
     * @param dice     the selected die from the player
     * @param increase if true increase the die value, if false decrease the die value
     */

    public synchronized void grozingPliersMove(Dice dice, Boolean increase) {
        if (toolCardLock.get()) {
            for (Dice diceInPool : board.getDraftedDice()) {
                if (dice.toString().equals(diceInPool.toString())) {
                    if (increase) diceInPool.increasesByOneValue();
                    else diceInPool.decreasesByOneValue();
                }
            }
            wakeUpToolCardThread();
        }
    }

    /**
     * GROZING PLIERS: Response
     * <p>
     * Sends to the players the updated data:
     */
    public void grozingPliersResponse() {
        if(toolCardLock.get()) {
            playerBroadcaster.broadcastResponseToAll(new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
            try {
                currentRound.getCurrentPlayer().getUserObserver().sendResponse(new AvailablePositionsResponse(sendAvailablePositions(currentRound.getCurrentPlayer())));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized void fluxBrushMove(Dice selectedDice) {
        if (toolCardLock.get()) {

            FluxBrush fluxBrush = (FluxBrush) getSelectedToolCard("FluxBrush");

            assert fluxBrush != null;

            List<Dice> list = new ArrayList<>();
            for (Dice dice : getDraftedDice()) {
                list.add(new Dice(dice.getFaceUpValue(),dice.getDiceColor()));
            }

            fluxBrush.setTemporaryDraftedDice(list);

            for (Dice diceInPool : fluxBrush.getTemporaryDraftedDice()) {
                if (selectedDice.toString().equals(diceInPool.toString())) {
                    diceInPool.roll();
                    Map<String, Boolean[][]> availablePositions = getCurrentRound().getCurrentPlayer().getPatternCard().computeAvailablePositionsDraftedDice(fluxBrush.getTemporaryDraftedDice());
                    try {
                        getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxBrushResponse(fluxBrush.getTemporaryDraftedDice(), diceInPool, availablePositions));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public synchronized void fluxBrushMove(Dice dice, int rowIndex, int columnIndex) {
        if (toolCardLock.get()) {
            FluxBrush fluxBrush = (FluxBrush) getSelectedToolCard("FluxBrush");
            board.setDraftedDice(fluxBrush.getTemporaryDraftedDice());
            placeDiceToolCard(dice, rowIndex, columnIndex);
        }
    }

    public void fluxBrushMove() {
        if (toolCardLock.get()) {
            wakeUpToolCardThread();
        }
    }

    public void fluxBrushResponse() {
        if (toolCardLock.get()) {
            playerBroadcaster.broadcastResponseToAll(new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
            playerBroadcaster.broadcastResponseToAll(new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions(getCurrentRound().getCurrentPlayer())));
        }
    }

    public synchronized void fluxRemoverMove(Dice selectedDice) {
        if (toolCardLock.get()) {
            FluxRemover fluxRemover = (FluxRemover) getSelectedToolCard("FluxRemover");

            fluxRemover.setDiceFromBag(board.draftOneDice());
            List<Dice> list = new ArrayList<>(getDraftedDice());
            list.add(fluxRemover.getDiceFromBag());
            fluxRemover.setDraftedDice(list);

            for (Dice dice : fluxRemover.getDraftedDice()) {
                if (dice.toString().equals(selectedDice.toString())) {
                    selectedDice = dice;
                    break;
                }
            }

            fluxRemover.getDraftedDice().remove(selectedDice);


            try {
                getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxRemoverResponse(fluxRemover.getDiceFromBag()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void fluxRemoverMove(Dice selectedDice, int chosenValue) {
        if (toolCardLock.get()) {
            FluxRemover fluxRemover = (FluxRemover) getSelectedToolCard("FluxRemover");

            for (Dice dice : fluxRemover.getDraftedDice()) {
                if (selectedDice.toString().equals(dice.toString())) {
                    dice.setFaceUpValue(chosenValue);
                    selectedDice.setFaceUpValue(chosenValue);
                }
            }
            Map<String, Boolean[][]> availablePositions = getCurrentRound().getCurrentPlayer().getPatternCard().computeAvailablePositionsDraftedDice(fluxRemover.getDraftedDice());
            try {
                getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxRemoverResponse(fluxRemover.getDraftedDice(), selectedDice, availablePositions));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void fluxRemoverMove(Dice selectedDice, int rowIndex, int columnIndex) {
        if (toolCardLock.get()) {
            FluxRemover fluxRemover = (FluxRemover) getSelectedToolCard("FluxRemover");

            board.addDiceToBag(fluxRemover.getDiceFromBag());
            board.setDraftedDice(fluxRemover.getDraftedDice());
            placeDiceToolCard(selectedDice, rowIndex, columnIndex);
        }
    }

    private ToolCard getSelectedToolCard(String toolCardName) {
        for (ToolCard toolCard : toolCards) {
            if (toolCard.getName().equals(toolCardName)) return toolCard;
        }
        return null;
    }

    /**
     * If the dice can't be placed in the pattern card
     */
    public synchronized void fluxRemoverMove() {
        if (toolCardLock.get()) {
            FluxRemover fluxRemover = (FluxRemover) getSelectedToolCard("FluxRemover");
            board.addDiceToBag(fluxRemover.getDiceFromBag());
            board.setDraftedDice(fluxRemover.getDraftedDice());
            wakeUpToolCardThread();
        }
    }

    public void fluxRemoverResponse() {
        if (toolCardLock.get()) {
            playerBroadcaster.broadcastResponseToAll(new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
            playerBroadcaster.broadcastResponseToAll(new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions((getCurrentRound().getCurrentPlayer()))));
        }
    }


    public synchronized void grindingStoneMove(Dice selectedDice) {
        if (toolCardLock.get()) {
            for (Dice diceInPool : board.getDraftedDice()) {
                if (selectedDice.toString().equals(diceInPool.toString())) {
                    diceInPool.setOppositeFace();
                }
            }
            wakeUpToolCardThread();
        }
    }

    public void grindingStoneResponse() {
        if (toolCardLock.get()) {
            playerBroadcaster.broadcastResponseToAll(new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
            try {
                currentRound.getCurrentPlayer().getUserObserver().sendResponse(new AvailablePositionsResponse(sendAvailablePositions(currentRound.getCurrentPlayer())));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void copperFoilBurnisherMove(Tuple dicePosition, Tuple position) {
        if (toolCardLock.get()) {
            List<List<Box>> patternCard = currentRound.getCurrentPlayer().getPatternCard().getGrid();
            patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
            patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
            wakeUpToolCardThread();
        }
    }

    public void copperFoilBurnisherResponse() {
        if (toolCardLock.get()) {
            playerBroadcaster.broadcastResponseToAll(new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions(getCurrentRound().getCurrentPlayer())));
        }
    }

    public synchronized void corkBackedStraightedgeMove(Dice selectedDice, int row, int column) {
        if (toolCardLock.get()) {
            Map<String, Boolean[][]> availablePositions = currentRound.getCurrentPlayer().getPatternCard().computeAvailablePositionsNoDiceAround(board.getDraftedDice());
            if (availablePositions.get(selectedDice.toString())[row][column])
                placeDiceForPlayer(selectedDice, row, column);
            wakeUpToolCardThread();
        }
    }

    public void corkBackedStraightedgeResponse() {
        if (toolCardLock.get()) {
            playerBroadcaster.broadcastResponseToAll(new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), currentRound.getCurrentPlayer().getPatternCard().computeAvailablePositions()));
        }
    }

    public synchronized void lensCutterMove(int roundIndex, String roundTrackDice, String poolDice) {
        if (toolCardLock.get()) {
            Dice fromTrackDice;
            Dice fromPoolDice;
            for (int i = 0; i < roundTrack.get(roundIndex).size(); i++) {
                if (roundTrack.get(roundIndex).get(i).toString().equals(roundTrackDice)) {
                    fromTrackDice = roundTrack.get(roundIndex).get(i);
                    roundTrack.get(roundIndex).remove(i);
                    for (int j = 0; j < getDraftedDice().size(); j++) {
                        if (getDraftedDice().get(j).toString().equals(poolDice)) {
                            fromPoolDice = getDraftedDice().get(j);
                            getDraftedDice().remove(j);
                            getDraftedDice().add(fromTrackDice);
                            roundTrack.get(roundIndex).add(fromPoolDice);
                            break;
                        }
                    }
                }
                System.err.println("Error: selected dice does not exist");
            }
            wakeUpToolCardThread();
        }
    }

    public void lensCutterResponse() {
        if (toolCardLock.get()) {
            playerBroadcaster.broadcastResponseToAll(new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
            playerBroadcaster.broadcastResponseToAll(new RoundTrackToolCardResponse(roundTrack));
            try {
                getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new AvailablePositionsResponse(getCurrentRound().getCurrentPlayer().getPatternCard().computeAvailablePositionsDraftedDice(getDraftedDice())));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void eglomiseBrushMove(Tuple dicePosition, Tuple position) {
        if (toolCardLock.get()) {
            List<List<Box>> patternCard = currentRound.getCurrentPlayer().getPatternCard().getGrid();
            if (patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice() != null) {
                patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
                patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
            } else System.out.println("Eglomise Brusher: Error invalid selected dice");
            wakeUpToolCardThread();
        }
    }

    public void eglomiseBrushResponse() {
        if (toolCardLock.get()) {
            playerBroadcaster.broadcastResponseToAll(new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions((getCurrentRound().getCurrentPlayer()))));
        }
    }

    public synchronized void lathekinMove(Tuple dicePosition, Tuple position, boolean doubleMove) {
        if (toolCardLock.get()) {
            Lathekin lathekin = (Lathekin) getSelectedToolCard("Lathekin");

            if (lathekin.getNewGrid() == null) {
                lathekin.setOldGrid(copyPatternCard());
            } else {
                getCurrentRound().getCurrentPlayer().getPatternCard().setGrid(lathekin.getNewGrid());
            }

            List<List<Box>> grid = currentRound.getCurrentPlayer().getPatternCard().getGrid();

            if (grid.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice() != null) {
                if (!doubleMove) {
                    System.out.println("Lathekin single move");
                    grid.get(position.getFirst()).get(position.getSecond()).insertDice(grid.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
                    grid.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
                } else {
                    System.out.println("Lathekin second move");
                    Dice dice = grid.get(position.getFirst()).get(position.getSecond()).getDice();
                    grid.get(position.getFirst()).get(position.getSecond()).removeDice();
                    grid.get(position.getFirst()).get(position.getSecond()).insertDice(grid.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
                    grid.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
                    grid.get(dicePosition.getFirst()).get(dicePosition.getSecond()).insertDice(dice);
                    this.doubleMove.set(true);
                }
            } else
                System.out.println("Lathekin: Error invalid selected dice");

            System.out.println("Waking up toolcard thread");

            if (lathekin.getNewGrid() == null) {
                try {
                    PatternCard patternCard =getCurrentRound().getCurrentPlayer().getPatternCard();
                    getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LathekinResponse(getCurrentRound().getCurrentPlayer().getPlayerUsername(), patternCard, patternCard.computeAvailablePositionsLathekin(), true));


                    lathekin.setNewGrid(getCurrentRound().getCurrentPlayer().getPatternCard().getGrid());
                    getCurrentRound().getCurrentPlayer().getPatternCard().setGrid(lathekin.getOldGrid());

                    System.out.println("sending data for the second lathekin move");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            wakeUpToolCardThread();
        }
    }


    public void lathekinResponse() {
        if (toolCardLock.get()) {
            System.out.println("sending Lathekin response");
            playerBroadcaster.broadcastResponseToAll(new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions((getCurrentRound().getCurrentPlayer()))));
        }
    }

    private synchronized List<List<Box>> copyPatternCard() {
        List<List<Box>> gridPattern = new ArrayList<>();
        for (int i = 0; i < currentRound.getCurrentPlayer().getPatternCard().getGrid().size(); i++) {
            gridPattern.add(new ArrayList<>());
            for (int j = 0; j < currentRound.getCurrentPlayer().getPatternCard().getGrid().get(i).size(); j++) {
                Box box = currentRound.getCurrentPlayer().getPatternCard().getGrid().get(i).get(j);
                if (box.isValueSet()) {
                    gridPattern.get(i).add(new Box(box.getValue()));
                } else gridPattern.get(i).add(new Box(box.getColor()));
                if (box.getDice() != null)
                    gridPattern.get(i).get(j).insertDice(box.getDice());
            }
        }
        return gridPattern;
    }

    public synchronized void runningPliersMove(Dice selectedDice, int rowIndex, int columnIndex) {
        if (toolCardLock.get()) {
            placeDiceToolCard(selectedDice, rowIndex, columnIndex);
        }
    }

    public void runningPliersResponse() {
        playerBroadcaster.broadcastResponseToAll(new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions(getCurrentRound().getCurrentPlayer())));
        playerBroadcaster.broadcastResponseToAll(new DraftedDiceToolCardResponse(getDraftedDice(), true));
    }

    public boolean getdoubleMove() {
        return doubleMove.get();
    }

    public synchronized void tapWheelMove(Dice roundTrackDice, int phase, Tuple dicePosition, Tuple position, boolean doubleMove) {
        if (toolCardLock.get()) {
            if (phase == -1) {
                setDoubleMove(true);
                wakeUpToolCardThread();
                tapWheelResponse(null, 3);
            }
            if (phase == 0) {
                System.out.println("Calculating the mask");
                Map<String, Boolean[][]> availablePositions = currentRound.getCurrentPlayer().getPatternCard().computeAvailablePositionsTapWheel(roundTrackDice, false);
                tapWheelResponse(availablePositions, 1);
            }
            if (phase == 1) {
                List<List<Box>> patternCard = currentRound.getCurrentPlayer().getPatternCard().getGrid();
                if (!doubleMove) {
                    patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());

                    Dice dice1 = patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice();
                    patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();

                    Map<String, Boolean[][]> hashMapGrid = currentRound.getCurrentPlayer().getPatternCard().computeAvailablePositionsTapWheel(dice1, true);
                    hashMapGrid.remove(dice1.toString() + position.getFirst() + position.getSecond());

                    System.out.println("The dice removed is\t" + dice1.toString() + position.getFirst() + position.getSecond());

                    wakeUpToolCardThread();

                    tapWheelResponse(hashMapGrid, 2);
                } else {
                    System.out.println("doubleMove");
                    Dice dice = patternCard.get(position.getFirst()).get(position.getSecond()).getDice();
                    patternCard.get(position.getFirst()).get(position.getSecond()).removeDice();
                    patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
                    patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
                    patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).insertDice(dice);
                    setDoubleMove(true);
                    wakeUpToolCardThread();
                    tapWheelResponse(null, 3);
                }
            }
            if (phase == 2) {

                List<List<Box>> patternCard = currentRound.getCurrentPlayer().getPatternCard().getGrid();
                patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
                patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();

                wakeUpToolCardThread();
                tapWheelResponse(null, 3);
            }
        }
    }

    public void setDoubleMove(boolean doubleMove) {
        this.doubleMove.set(doubleMove);
    }

    private void tapWheelResponse(Map<String, Boolean[][]> availablePositions, int phase) {
        if (toolCardLock.get()) {
            if (phase == 1) {
                try {
                    getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new TapWheelResponse(availablePositions, getCurrentRound().getCurrentPlayer(), 1));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            if (phase == 2) {
                try {
                    getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new TapWheelResponse(availablePositions, getCurrentRound().getCurrentPlayer(), 2));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            if (phase == 3) {
                playerBroadcaster.broadcastResponseToAll(new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions((getCurrentRound().getCurrentPlayer()))));
                currentRound.toolCardMoveDone();
            }
        }
    }
}

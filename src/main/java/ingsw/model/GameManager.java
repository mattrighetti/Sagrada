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
import ingsw.utilities.Broadcaster;
import ingsw.utilities.ControllerTimer;
import ingsw.utilities.MoveStatus;
import ingsw.utilities.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that handles the entire game process and modifies the model itself
 */
public class GameManager {
    private Board board;
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
    public final Object toolCardLock;
    private Set<Player> disconnectedPlayers;

    /**
     * Creates an instance of GameManager with every object needed by the game itself and initializes its players
     * assigning to each of them a PrivateObjectiveCard and asking them to choose a PatternCard.
     *
     * @param players players that joined the match
     */
    public GameManager(List<Player> players, Controller controller) {
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
        toolCardLock = new Object();
        turnInRound = new AtomicInteger(0);
        disconnectedPlayers = new HashSet<>();
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
        // Collections.shuffle(toolCards);
        //return new ArrayList<>(this.toolCards.subList(0, 3));
        ArrayList<ToolCard> toolCards = new ArrayList<>();
        toolCards.add(new LensCutter());
        toolCards.add(new RunningPliers());
        toolCards.add(new TapWheel());
        return toolCards;
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
    public void pickPatternCards() {
        for (Player player : playerList) {

            try {
                //Need to create another List: subList is not Serializable
                List<PatternCard> patternCardArrayList = new ArrayList<>(patternCards.subList(0, 4));
                player.getUserObserver().sendResponse(new PatternCardNotification(patternCardArrayList));
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 4; i++) {
                patternCards.remove(0);
            }
        }
    }

    public int getNoOfCurrentRound(){
        return roundTrack.size() + 1;
    }

    private void deleteMatch() {
        controller.removeMatch();
    }

    /**
     * Method that checks if every user is connected to the game.
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

                        Thread.sleep(500);
                        player.getUserObserver().sendResponse(new DraftedDiceResponse(board.getDraftedDice()));
                    } else if (!disconnectedPlayers.contains(player) && !player.getUser().isActive()) {
                        System.out.println("User " + player.getPlayerUsername() + " has disconnected, adding it to disconnected Users iterating Player "+ player.getPlayerUsername() + " " + disconnectedPlayers.size() + " " + (playerList.size() - 1));
                        disconnectedPlayers.add(player);

                    } else {
                        // Check if the User is disconnected or not
                        // If it's disconnected the catch block will handle the disconnection
                        player.getUserObserver();
                    }

                    // If there's only a user connected then...
                } else {
                    // TODO crea un metodo testabile, poco reliable nel caso in cui ci siano più utenti nella lista
                    for (Player winner : playerList) {
                        if (winner.getUser().isActive()) {
                            winner.getUser().incrementNoOfWins();
                            winner.getUserObserver().notifyVictory(0);
                        }
                    }
                    stop.set(true);
                    deleteMatch();
                }

            } catch (RemoteException e) {
                // If a RMI user disconnects, this code will execute
                System.out.println("RMI User " + player.getPlayerUsername() + " disconnected");
                disconnectedPlayers.add(player);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
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
                    Thread.sleep(2000);

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
    public void waitForEveryPatternCard() {
        new Thread(() -> {
            waitAck();
            resetAck();
            BoardDataResponse boardDataResponse = new BoardDataResponse(playerList, choosePublicObjectiveCards(), chooseToolCards());
            Broadcaster.broadcastResponseToAll(playerList, boardDataResponse);
            this.board = new Board(boardDataResponse.publicObjectiveCards, boardDataResponse.toolCards);
            listenForPlayerDisconnection();
            startMatch();
        }).start();
    }

    /**
     * Method that drafts the dice from the board and sends them to every user view
     */
    public void draftDiceFromBoard() {
        Broadcaster.broadcastResponseToAll(playerList, board.draftDice(playerList.size()));
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

    public void placeDiceForPlayer(Dice dice, int rowIndex, int columnIndex) {
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
                Thread.sleep(500);
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
                        i++;
                    } else {
                        shiftPlayerList();
                    }
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
            if (winner != null && winner.equals(player)) {
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

            System.out.println("Turn forward " + i + " player " + playerList.get(i));

            currentRound.setPlayerEndedTurn(false);

            if (playerList.get(i).getUser().isActive()) {
                currentRound.startForPlayer(playerList.get(i));
                startTimer(40000);

                //wait until turn has ended
                waitEndTurn();
            }
        }
        turnInRound.set(2);
        for (int i = playerList.size() - 1; i >= 0; i--) {

            System.out.println("Turn backward " + i + " player " + playerList.get(i));

            currentRound.setPlayerEndedTurn(false);
            if (playerList.get(i).getUser().isActive()) {
                currentRound.startForPlayer(playerList.get(i));
                startTimer(40000);

                //wait until turn has ended
                waitEndTurn();
            }
        }

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

    private void shiftPlayerList() {
        Player tmp = playerList.get(0);
        playerList.remove(0);
        playerList.add(tmp);
        endRound.set(true);
    }

    private void startTimer(long time) {
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
                    currentRound.getCurrentPlayer().getUserObserver().sendResponse(new TimeOutResponse());
                    Thread.sleep(500);
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
        Broadcaster.broadcastResponseToAll(playerList, new RoundTrackNotification(roundTrack.get(round)));
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

    boolean makeMove(Player player, Dice dice, int rowIndex, int columnIndex) {
        if (player.getPatternCard().getGrid().get(rowIndex).get(columnIndex).getDice() == null) {


            player.getPatternCard().getGrid().get(rowIndex).get(columnIndex).insertDice(dice);
            board.getDraftedDice().remove(dice);

            // Send updated draftedDice
            Broadcaster.broadcastResponseToAll(playerList, board.getDraftedDice());

            // Update MovesHistory
            addMoveToHistoryAndNotify(new MoveStatus(player.getPlayerUsername(),
                    "Placed dice" + dice + " in [" + rowIndex + ", " + columnIndex + "]"));

            // UpdateView response
            Broadcaster.broadcastResponseToAll(playerList, new UpdateViewResponse(player, sendAvailablePositions(getCurrentRound().getCurrentPlayer())));
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
        new Thread(
                () -> {
                    for (ToolCard toolCard : toolCards) {
                        if (toolCard.getName().equals(toolCardName)) {
                            addMoveToHistoryAndNotify(new MoveStatus("Get the name", "Used toolcard " + toolCardName));
                            currentRound.makeMove(toolCard);

                        }
                    }
                }).start();
    }

    /**
     * Method that will update the current Moves made by each Player in every User's View
     *
     * @param moveStatus move to be added in the List of Moves made
     */
    private void addMoveToHistoryAndNotify(MoveStatus moveStatus) {
        movesHistory.add(moveStatus);
        Broadcaster.updateMovesHistory(playerList, movesHistory);
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


    public void glazingHammerResponse() {
        for (Dice dice : board.getDraftedDice()) {
            dice.roll();
        }
        boolean endTurnCheck = false;
        if (currentRound.getNoOfMoves() == 0) endTurnCheck = true;
        Broadcaster.broadcastResponseToAll(playerList, new DraftedDiceToolCardResponse(board.getDraftedDice(), endTurnCheck));
        currentRound.toolCardMoveDone();
    }

    public void grozingPliersMove(Dice dice, Boolean increase) {
        for (Dice diceInPool : board.getDraftedDice()) {
            if (dice.toString().equals(diceInPool.toString())) {
                if (increase) diceInPool.increasesByOneValue();
                else diceInPool.decreasesByOneValue();
            }
        }
        wakeUpToolCardThread();
    }

    private void placeDiceToolCard(Dice dice, int rowIndex, int columnIndex) {
        Player player = getCurrentRound().getCurrentPlayer();
        if (player.getPatternCard().getGrid().get(rowIndex).get(columnIndex).getDice() == null) {
            player.getPatternCard().getGrid().get(rowIndex).get(columnIndex).insertDice(dice);
            board.getDraftedDice().remove(dice);
        }
        synchronized (toolCardLock) {
            toolCardLock.notifyAll();
        }
    }

    public void grozingPliersResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
        try {
            currentRound.getCurrentPlayer().getUserObserver().sendResponse(new AvailablePositionsResponse(sendAvailablePositions(currentRound.getCurrentPlayer())));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        currentRound.toolCardMoveDone();
    }


    public void fluxBrushMove(Dice selectedDice) {
        for (Dice diceInPool : board.getDraftedDice()) {
            if (selectedDice.toString().equals(diceInPool.toString())) {
                diceInPool.roll();
                Map<String, Boolean[][]> availablePositions = getCurrentRound().getCurrentPlayer().getPatternCard().computeAvailablePositionsDraftedDice(board.getDraftedDice());
                try {
                    getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxBrushResponse(board.getDraftedDice(), diceInPool, availablePositions));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void fluxBrushMove(Dice dice, int rowIndex, int columnIndex) {
        placeDiceToolCard(dice, rowIndex, columnIndex);
    }

    public void fluxBrushMove() {
        wakeUpToolCardThread();
    }

    public void fluxBrushResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
        Broadcaster.broadcastResponseToAll(playerList, new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions(getCurrentRound().getCurrentPlayer())));
        currentRound.toolCardMoveDone();
    }

    public void fluxRemoverMove(Dice selectedDice) {
        for (int i = 0; i < board.getDraftedDice().size(); i++) {
            if (board.getDraftedDice().get(i).toString().equals(selectedDice.toString()))
                board.getDraftedDice().remove(i);
        }
        List<Dice> diceList = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            Dice dice = new Dice(selectedDice.getDiceColor());
            dice.setFaceUpValue(i);
            diceList.add(dice);
        }
        try {
            getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxRemoverResponse(board.draftOneDice()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void fluxRemoverMove(Dice selectedDice, int chosenValue) {
        for (Dice dice : getDraftedDice()) {
            if (selectedDice.toString().equals(dice.toString())) {
                dice.setFaceUpValue(chosenValue);
                selectedDice.setFaceUpValue(chosenValue);
            }
        }
        Map<String, Boolean[][]> availablePositions = getCurrentRound().getCurrentPlayer().getPatternCard().computeAvailablePositionsDraftedDice(board.getDraftedDice());
        try {
            getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxRemoverResponse(getDraftedDice(), selectedDice, availablePositions));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void fluxRemoverMove(Dice selectedDice, int rowIndex, int columnIndex) {
        placeDiceToolCard(selectedDice, rowIndex, columnIndex);
    }

    public void fluxRemoverMove() {
        wakeUpToolCardThread();
    }

    public void fluxRemoverResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
        Broadcaster.broadcastResponseToAll(playerList, new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions((getCurrentRound().getCurrentPlayer()))));
        currentRound.toolCardMoveDone();
    }

    public void grindingStoneMove(Dice selectedDice) {
        for (Dice diceInPool : board.getDraftedDice()) {
            if (selectedDice.toString().equals(diceInPool.toString())) {
                diceInPool.setOppositeFace();
            }
        }
        wakeUpToolCardThread();
    }

    public void grindingStoneResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
        try {
            currentRound.getCurrentPlayer().getUserObserver().sendResponse(new AvailablePositionsResponse(sendAvailablePositions(currentRound.getCurrentPlayer())));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        currentRound.toolCardMoveDone();
    }

    public void copperFoilBurnisherMove(Tuple dicePosition, Tuple position) {
        List<List<Box>> patternCard = currentRound.getCurrentPlayer().getPatternCard().getGrid();
        patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
        patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
        wakeUpToolCardThread();
    }

    public void copperFoilBurnisherResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions(getCurrentRound().getCurrentPlayer())));
        currentRound.toolCardMoveDone();
    }

    public void corkBackedStraightedgeMove(Dice selectedDice, int row, int column) {
        Map<String, Boolean[][]> availablePositions = currentRound.getCurrentPlayer().getPatternCard().computeAvailablePositionsNoDiceAround(board.getDraftedDice());
        if (availablePositions.get(selectedDice.toString())[row][column])
            placeDiceForPlayer(selectedDice, row, column);
        wakeUpToolCardThread();
    }

    public void corkBackedStraightedgeResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), currentRound.getCurrentPlayer().getPatternCard().computeAvailablePositions()));
        currentRound.toolCardMoveDone();
    }

    public void lensCutterMove(int roundIndex, String roundTrackDice, String poolDice) {
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

    public void lensCutterResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new DraftedDiceToolCardResponse(board.getDraftedDice(), false));
        Broadcaster.broadcastResponseToAll(playerList, new RoundTrackToolCardResponse(roundTrack));
        try {
            getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new AvailablePositionsResponse(getCurrentRound().getCurrentPlayer().getPatternCard().computeAvailablePositionsDraftedDice(getDraftedDice())));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        currentRound.toolCardMoveDone();
    }

    public void eglomiseBrushMove(Tuple dicePosition, Tuple position) {
        List<List<Box>> patternCard = currentRound.getCurrentPlayer().getPatternCard().getGrid();
        if (patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice() != null) {
            patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
            patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
        } else System.out.println("Eglomise Brusher: Error invalid selected dice");
        wakeUpToolCardThread();
    }

    public void eglomiseBrushResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions((getCurrentRound().getCurrentPlayer()))));
        currentRound.toolCardMoveDone();
        ControllerTimer.get().cancelTimer();
    }

    public void lathekinMove(Tuple dicePosition, Tuple position, boolean doubleMove) {
        List<List<Box>> patternCard = currentRound.getCurrentPlayer().getPatternCard().getGrid();
        if (patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice() != null) {
            if (!doubleMove) {
                patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
                patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
            } else {
                Dice dice = patternCard.get(position.getFirst()).get(position.getSecond()).getDice();
                patternCard.get(position.getFirst()).get(position.getSecond()).removeDice();
                patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
                patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
                patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).insertDice(dice);
                this.doubleMove.set(true);
            }
        } else
            System.out.println("Lathekin: Error invalid selected dice");
        wakeUpToolCardThread();
    }

    public void lathekinResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions((getCurrentRound().getCurrentPlayer()))));
    }


    public void runningPliersMove(Dice selectedDice, int rowIndex, int columnIndex) {
        placeDiceToolCard(selectedDice, rowIndex, columnIndex);
    }

    public void runningPliersResponse() {
        Broadcaster.broadcastResponseToAll(playerList, new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions(getCurrentRound().getCurrentPlayer())));
        Broadcaster.broadcastResponseToAll(playerList, new DraftedDiceToolCardResponse(getDraftedDice(), true));
    }

    public boolean getdoubleMove() {
        return doubleMove.get();
    }

    public void tapWheelMove(Dice roundTrackDice, int phase, Tuple dicePosition, Tuple position, boolean doubleMove) {
        if (phase == -1) {
            setDoubleMove(true);
            wakeUpToolCardThread();
            tapWheelResponse(null, getCurrentRound().getCurrentPlayer().getPatternCard(), 3);
        }
        if (phase == 0) {
            System.out.println("Calculating the mask");
            Map<String, Boolean[][]> availablePositions = currentRound.getCurrentPlayer().getPatternCard().computeAvailablePositionsTapWheel(roundTrackDice, false);
            tapWheelResponse(availablePositions, currentRound.getCurrentPlayer().getPatternCard(), 1);
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

                tapWheelResponse(hashMapGrid, currentRound.getCurrentPlayer().getPatternCard(), 2);
            } else {
                System.out.println("doubleMove");
                Dice dice = patternCard.get(position.getFirst()).get(position.getSecond()).getDice();
                patternCard.get(position.getFirst()).get(position.getSecond()).removeDice();
                patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
                patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();
                patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).insertDice(dice);
                setDoubleMove(true);
                wakeUpToolCardThread();
                tapWheelResponse(null, getCurrentRound().getCurrentPlayer().getPatternCard(), 3);
            }
        }
        if (phase == 2) {
            if (!this.doubleMove.get()) {
                List<List<Box>> patternCard = currentRound.getCurrentPlayer().getPatternCard().getGrid();
                patternCard.get(position.getFirst()).get(position.getSecond()).insertDice(patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).getDice());
                patternCard.get(dicePosition.getFirst()).get(dicePosition.getSecond()).removeDice();

                wakeUpToolCardThread();
                tapWheelResponse(null, getCurrentRound().getCurrentPlayer().getPatternCard(), 3);

            }
        }
    }

    private void wakeUpToolCardThread() {
        synchronized (toolCardLock) {
            toolCardLock.notifyAll();
        }
    }

    public void setDoubleMove(boolean doubleMove) {
        this.doubleMove.set(doubleMove);
    }

    public void avoidToolCardUse() {
        try {
            currentRound.getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void tapWheelResponse(Map<String, Boolean[][]> availablePositions, PatternCard patternCard, int phase) {
        if (phase == 1) {
            try {
                getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new TapWheelResponse(availablePositions, patternCard, 1));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (phase == 2) {
            try {
                getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new TapWheelResponse(availablePositions, patternCard, 2));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (phase == 3) {
            Broadcaster.broadcastResponseToAll(playerList, new PatternCardToolCardResponse(currentRound.getCurrentPlayer(), sendAvailablePositions((getCurrentRound().getCurrentPlayer()))));
            currentRound.toolCardMoveDone();
        }
    }
}

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

import ingsw.controller.network.commands.*;
import ingsw.model.cards.patterncard.*;
import ingsw.model.cards.privateoc.*;
import ingsw.model.cards.publicoc.*;
import ingsw.model.cards.toolcards.*;
import ingsw.utilities.Broadcaster;
import ingsw.utilities.MoveStatus;

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
    private List<MoveStatus> movesHistory;
    private AtomicInteger noOfAck;
    private List<Player> playerList;
    private List<PrivateObjectiveCard> privateObjectiveCards;
    private List<PublicObjectiveCard> publicObjectiveCards;
    private List<ToolCard> toolCards;
    private List<PatternCard> patternCards;
    private List<List<Dice>> roundTrack;
    private Round currentRound;
    private boolean brokenWindow;
    private AtomicBoolean endRound;
    private Thread diceAckThread;
    private Thread matchThread;
    private Thread userDisconnectionListenerThread;

    /**
     * Creates an instance of GameManager with every object needed by the game itself and initializes its players
     * assigning to each of them a PrivateObjectiveCard and asking them to choose a PatternCard.
     *
     * @param players players that joined the match
     */
    public GameManager(List<Player> players) {
        noOfAck = new AtomicInteger(0);
        endRound = new AtomicBoolean(false);
        brokenWindow = false;
        playerList = players;
        roundTrack = new ArrayList<>();
        movesHistory = new LinkedList<>();
        setUpGameManager();
    }

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

    private void setUpToolCards() {
        toolCards = new LinkedList<>();
        this.toolCards.add(new CopperFoilBurnisher());
        this.toolCards.add(new CorkBarckedStraightEdge());
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

    private void setUpPrivateObjectiveCards() {
        privateObjectiveCards = new LinkedList<>();
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.BLUE));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.GREEN));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.RED));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.PURPLE));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.YELLOW));
        Collections.shuffle(privateObjectiveCards);
    }

    private List<ToolCard> chooseToolCards() {
        Collections.shuffle(toolCards);
        return new ArrayList<>(this.toolCards.subList(0, 3));
    }

    private List<PublicObjectiveCard> choosePublicObjectiveCards() {
        Collections.shuffle(publicObjectiveCards);
        return new ArrayList<>(publicObjectiveCards.subList(0, 3));
    }

    public void pickPatternCards() {
        for (Player player : playerList) {
            try {
                //Need to create another List: subList is not Serializable
                ArrayList<PatternCard> patternCardArrayList = new ArrayList<>(patternCards.subList(0, 4));
                player.getUserObserver().sendResponse(new PatternCardNotification(patternCardArrayList));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 4; i++) {
                patternCards.remove(0);
            }
        }
    }

    private void listenForPlayerDisconnection() {
        Set<Player> disconnectedPlayerSet = new HashSet<>();
        userDisconnectionListenerThread = new Thread(() -> {
            do {

                try {

                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }

                for (Player player : playerList) {
                    try {
                        // Se l'utente era nel set di utenti disconnessi e ora è attivo allora inviagli la boardDataResponse
                        if (disconnectedPlayerSet.contains(player) && player.getUser().isActive()) {
                            System.out.println("Reactivated user: " + player.getPlayerUsername() + " sending data");
                            disconnectedPlayerSet.remove(player);
                            player.getUserObserver().sendResponse(new BoardDataResponse(playerList, publicObjectiveCards, toolCards));
                        } else if (!disconnectedPlayerSet.contains(player) && !player.getUser().isActive()) {
                            System.out.println("Adding back Socket player");
                            disconnectedPlayerSet.add(player);
                        } else {
                            //Altrimenti controlla se è ancora connesso
                            player.getUserObserver();
                            System.out.println("Else");
                        }
                    } catch (RemoteException e) {
                        // Se un utente RMI è disconnesso viene lanciata l'eccezione e viene inserito negli utenti disconnessi
                        System.out.println("Exception thrown");
                        disconnectedPlayerSet.add(player);
                    }
                }
            } while (true);
        });
        userDisconnectionListenerThread.start();
    }

    List<Player> getPlayerList() {
        return playerList;
    }


    public PatternCard setPatternCardForPlayer(String username, PatternCard patternCard) {
        for (Player player : playerList) {
            if (player.getPlayerUsername().equals(username)) {
                player.setPatternCard(patternCard);
                receiveAck();
                synchronized (noOfAck) {
                    noOfAck.notify();
                }
            }
        }

        return patternCard;
    }

    void checkIfEveryUserIsActive() {

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
            this.board = new Board(boardDataResponse.publicObjectiveCards, boardDataResponse.toolCards, playerList);
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
        diceAckThread = new Thread(() -> {
            System.out.println("Waiting Dice Ack");
            waitAck();
            resetAck();
            startRound();
        });

        diceAckThread.setName("diceAck and round");
        diceAckThread.start();
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

    public void removeDice() {
        if (brokenWindow) {
            /*
             * 1 - rimuovi dado
             * 2 - if getPatternCard().isBreakable()
             *       notifica rimozione
             *     else
             *         brokenWindow = false;
             *         notifica mossa
             */
        }

    }

    public void breakWindow(Player player) {
        /*
         * if getPatternCard().isBreakable()
         *      brokenWindow = true
         *      disattiva tasti breakWindow di tutti gli utenti
         *      notifica rimozione
         */
    }

    public void endTurn() {
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
            noOfAck.notify();
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
        matchThread = new Thread(() -> {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            for (int i = 0; i < 10; i++) {
                notifyDraftToPlayer(playerList.get(0));
                endRound.set(false);

                System.out.println("Round " + i);
                //wait until the end of the round
                synchronized (endRound) {
                    while (!endRound.get()) {
                        try {
                            endRound.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        });

        matchThread.setName("match");
        matchThread.start();
    }

    /**
     * Method that starts a single round
     */
    private void startRound() {
        currentRound = new Round(this);
        //Rounds going forward
        for (int i = 0; i < playerList.size(); i++) {

            System.out.println("Turn forward " + i + " player " + playerList.get(i));

            currentRound.startForPlayer(playerList.get(i));

            //wait until turn has ended
            waitEndTurn();
        }

        for (int i = playerList.size() - 1; i >= 0; i--) {

            System.out.println("Turn backward " + i + " player " + playerList.get(i));

            currentRound.startForPlayer(playerList.get(i));

            //wait until turn has ended
            waitEndTurn();
        }

        if (!board.getDraftedDice().isEmpty()) {
            roundTrack.add(board.getDraftedDice());
            notifyUpdatedRoundTrack();
        }

        Player tmp = playerList.get(0);
        playerList.remove(0);
        playerList.add(tmp);
        endRound.set(true);

        //wake up the match thread
        synchronized (endRound) {
            endRound.notify();
        }
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
    List<Boolean[][]> sendAvailablePositions(Player player) {
        return player.getPatternCard().computeAvailablePositions(board.getDraftedDice());
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
            Broadcaster.broadcastResponseToAll(playerList, new UpdateViewResponse(player));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method called when an user selected a patternCard from the view
     *
     * @param toolCardName name of the ToolCard to use
     */
    public void useToolCard(String toolCardName) {
        for (ToolCard toolCard : toolCards) {
            if (toolCard.getName().equals(toolCardName)) {
                addMoveToHistoryAndNotify(new MoveStatus("Get the name", "Used toolcard " + toolCardName));
                currentRound.makeMove(toolCard);


            }
        }
    }

    void addMoveToHistoryAndNotify(MoveStatus moveStatus) {
        movesHistory.add(moveStatus);
        Broadcaster.updateMovesHistory(playerList, movesHistory);
    }

    //TOOL CARDS METHODS

    public void glazingHammer() {
        for (Dice dice : board.getDraftedDice()) {
            dice.roll();
        }
        Broadcaster.broadcastResponseToAll(playerList, new DraftPoolResponse(board.getDraftedDice()));
    }
}

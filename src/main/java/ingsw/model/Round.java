package ingsw.model;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.EndTurnResponse;
import ingsw.model.cards.toolcards.ToolCard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class that manages the execution of a single turn. Is created by ExecuteTurn() in GameManager
 */
public class Round implements Runnable {
    private Player player;
    private final AtomicBoolean hasMadeAMove;
    private GameManager gameManager;
    private final AtomicBoolean playerEndedTurn;
    public final List<String> blockedTurnPlayers;
    private int noOfMoves;
    private AtomicBoolean avoidEndTurnNotification;

    /**
     * Set the Game Manager and instantiates the field required for thread synchronization
     * @param gameManager Game Manager instance
     */
    public Round(GameManager gameManager) {
        this.gameManager = gameManager;
        hasMadeAMove = new AtomicBoolean();
        playerEndedTurn = new AtomicBoolean();
        blockedTurnPlayers = new ArrayList<>();
        avoidEndTurnNotification = new AtomicBoolean(false);
    }

    /**
     * Starts the turn for a specific player
     * @param player Current player
     */
    void startForPlayer(Player player) {
        this.player = player;
        hasMadeAMove.set(false);
        playerEndedTurn.set(false);
        run();
    }

    /**
     * Method that notify the user with an ActivateTurnNotification()
     * and goes in wait() two times (once per move). At the end sends an EndTurnResponse
     * and. if it's active it cancels the timer and notify ExecuteTurn(it was in WAIT status).
     */
    @Override
    public void run() {
        new Thread(() -> {
            if (!blockedTurnPlayers.contains(getCurrentPlayer().getPlayerUsername())) {
                try {
                    player.getUserObserver().activateTurnNotification(gameManager.sendAvailablePositions(player));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                avoidEndTurnNotification.set(false);
                noOfMoves = 0;
                hasMadeAMove.set(false);
                waitForMove();
                noOfMoves++;
                System.out.println("First move done");
                hasMadeAMove.set(false);
                waitForMove();
                noOfMoves++;
                System.out.println("Second move done");

                endTurnNotification();
            } else {
                blockedTurnPlayers.remove(getCurrentPlayer().getPlayerUsername());
            }

            System.out.println("End of Turn in Round");

            playerEndedTurn.set(true);

            gameManager.getControllerTimer().cancelTimer();

            //wake up the round thread
            synchronized (playerEndedTurn) {
                playerEndedTurn.notifyAll();
            }

        }).start();
    }

    /**
     * Sends the EndTurnResponse to the currentPlayer
     */
    private void endTurnNotification() {
        if (!avoidEndTurnNotification.get()) {
            try {
                getCurrentPlayer().getUserObserver().sendResponse(new EndTurnResponse());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method the set hasMadeAMove to true and wake the turn thread.
     */
    private void hasMadeAMove() {
        hasMadeAMove.set(true);

        //wake up the Thread of round class
        synchronized (hasMadeAMove) {
            hasMadeAMove.notifyAll();
        }
    }

    /**
     * Set the current Thread in WAIT and it will exit from the loop only if the player
     * makes a move or clicks on EndTurn.
     */
    private void waitForMove() {
        System.out.println("Wait for the move");
        synchronized (hasMadeAMove) {
            while (!(hasPlayerEndedTurn().get() || hasMadeAMove.get())) {
                //wait until the move is done
                try {
                    hasMadeAMove.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }

            }
            hasMadeAMove.set(false);
        }
    }

    /**
     * Terminates the current Turn Thread
     * @param hasPlayerEndedTurn
     */
    void setPlayerEndedTurn(boolean hasPlayerEndedTurn) {
        if (hasPlayerEndedTurn)
            System.out.println("End the turn");
        else System.out.println("Reset PlayerEndedTurn");
        playerEndedTurn.set(hasPlayerEndedTurn);


        //Wake up the round thread
        if (hasPlayerEndedTurn) {
            synchronized (hasMadeAMove) {
                hasMadeAMove.notifyAll();
            }
        }
    }

    /**
     * Returns if a player has ended the turn.
     * @return
     */
    AtomicBoolean hasPlayerEndedTurn() {
        return playerEndedTurn;
    }

    /**
     * Place-Dice move. Method for the placing dice move
     *
     * @param dice        to place
     * @param rowIndex    index of the row where to place dice in the pattern card
     * @param columnIndex index of the column where to place dice in pattern card
     */
    void makeMove(Dice dice, int rowIndex, int columnIndex) {
        if (gameManager.makeMove(player, dice, rowIndex, columnIndex)) {
            System.out.println("Move made");
            hasMadeAMove();

        }
    }

    /**
     * ToolCard move. Method for starting the ToolCard(it is executed in a thread).
     * It starts only if the current player has enough favour tokens
     * @param toolCard toolcard to use
     */
    void makeMove(ToolCard toolCard) {
        if (toolCard.getPrice() <= getCurrentPlayer().getFavourTokens()) {

            if (toolCard.getPrice() == 1) toolCard.increasePrice();
            toolCard.action(gameManager);

        } else {
            try {
                getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse(getCurrentPlayer().getFavourTokens()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Notify that a toolcar is used
     */
    public void toolCardMoveDone() {
        hasMadeAMove();
    }

    /**
     * Returns the current player
     * @return
     */
    public Player getCurrentPlayer() {
        return player;
    }

    @SuppressWarnings("unused")
    int getNoOfMoves() {
        return noOfMoves;
    }

    /**
     * Sets if the EndTurnNotification has to be executed or not
     * @param avoid
     */
    public void avoidEndTurnNotification(boolean avoid) {
        this.avoidEndTurnNotification.set(avoid);
    }
}

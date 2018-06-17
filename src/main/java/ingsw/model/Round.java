package ingsw.model;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.model.cards.toolcards.ToolCard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Round implements Runnable {
    private Thread playerMoves;
    private Player player;

    private final AtomicBoolean hasMadeAMove;
    private GameManager gameManager;
    private final AtomicBoolean playerEndedTurn;
    public List<String> blockedTurnPlayers;

    Round(GameManager gameManager) {
        this.gameManager = gameManager;
        hasMadeAMove = new AtomicBoolean();
        playerEndedTurn = new AtomicBoolean();
        blockedTurnPlayers = new ArrayList<>();
    }

    void startForPlayer(Player player) {
        this.player = player;
        hasMadeAMove.set(false);
        playerEndedTurn.set(false);
        run();
    }

    @Override
    public void run() {
        new Thread(() -> {
            try {
                player.getUserObserver().activateTurnNotification(gameManager.sendAvailablePositions(player));
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (!blockedTurnPlayers.contains(getCurrentPlayer().getPlayerUsername())) {
                hasMadeAMove.set(false);
                waitForMove();
                System.out.println("First move done");
                hasMadeAMove.set(false);
                waitForMove();
                System.out.println("Second move done");
            } else
                blockedTurnPlayers.remove(getCurrentPlayer().getPlayerUsername());

            playerEndedTurn.set(true);

            //wake up the round thread
            synchronized (playerEndedTurn) {
                playerEndedTurn.notify();
            }

        }).start();
    }

    public void hasMadeAMove() {
        hasMadeAMove.set(true);

        //wake up the Thread of round class
        synchronized (hasMadeAMove) {
            hasMadeAMove.notify();
        }
    }

    private void waitForMove() {
        System.out.println("Wait for the move");
        synchronized (hasMadeAMove) {
            while (!hasPlayerEndedTurn().get() || !hasMadeAMove.get()) {
                //wait until the move is done
                try {
                    hasMadeAMove.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            hasMadeAMove.set(false);
        }
    }

    void setPlayerEndedTurn(boolean hasPlayerEndedTurn) {
        if (hasPlayerEndedTurn)
            System.out.println("End the turn");
        else System.out.println("Reset PlayerEndedTurn");
        playerEndedTurn.set(hasPlayerEndedTurn);


        //Wake up the round thread
        if (hasPlayerEndedTurn) {
            synchronized (playerEndedTurn) {
                playerEndedTurn.notify();
            }
        }
    }

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

    void makeMove(ToolCard toolCard) {
        if (toolCard.getPrice() <= getCurrentPlayer().getFavourTokens() ) {

            if (toolCard.getPrice() == 1) toolCard.increasePrice();
            getCurrentPlayer().decreaseFavorTokens(toolCard.getPrice());
            toolCard.action(gameManager);

        } else {
            try {
                getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse(getCurrentPlayer().getFavourTokens()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void toolCardMoveDone() {
        hasMadeAMove();
    }

    public Player getCurrentPlayer() {
        return player;
    }
}

package ingsw.model;

import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Round implements Runnable {
    private Thread playerMoves;
    private Player player;

    private AtomicBoolean hasMadeAMove;
    private GameManager gameManager;
    private AtomicBoolean playerEndedTurn;

    public Round(GameManager gameManager) {
        this.gameManager = gameManager;
        hasMadeAMove = new AtomicBoolean();
        playerEndedTurn = new AtomicBoolean();
    }

    public void startForPlayer(Player player) {
        this.player = player;
        hasMadeAMove.set(false);
        playerEndedTurn.set(false);
        run();
    }

    @Override
    public void run() {
        playerMoves = new Thread( () -> {
            //TODO recheck activate turn
            try {
                player.getUserObserver().activateTurnNotification(gameManager.sendAvailablePositions(player));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            waitForMove();
            System.out.println("First move done");
            waitForMove();
            System.out.println("Second move done");
            playerEndedTurn.set(true);

            //wake up the round thread
            synchronized (playerEndedTurn){
                playerEndedTurn.notify();
            }

        });
        playerMoves.setName("Turn");
        playerMoves.start();
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
            while (!hasMadeAMove.get()) {
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

    public void setPlayerEndedTurn(boolean hasPlayerEndedTurn) {
        System.out.println("End the turn");
        playerEndedTurn.set(hasPlayerEndedTurn);

        //Wake up the round thread
        synchronized (playerEndedTurn) {
            playerEndedTurn.notify();
        }
    }

    public AtomicBoolean hasPlayerEndedTurn() {
        return playerEndedTurn;
    }

/*    public boolean makeMove(Dice dice, int rowIndex, int columnIndex) {
        //TODO ricontrolla se deve ritornare realmente valori
        boolean isMoveAccepted = gameManager.equals("");
        hasMadeAMove.set(isMoveAccepted);
        return isMoveAccepted;
    }*/

    /**
     * Place-Dice move. Method for the placing dice move
     * @param dice to place
     * @param rowIndex index of the row where to place dice in the pattern card
     * @param columnIndex index of the column where to place dice in pattern card
     */
    public void makeMove(Dice dice, int rowIndex, int columnIndex) {
        if (gameManager.makeMove(player, dice, rowIndex, columnIndex)) {
            System.out.println("Move made");
            hasMadeAMove();

        }
    }

    public void skipMove() {
       hasMadeAMove();
    }

    public Player getCurrentPlayer() {
        return player;
    }
}

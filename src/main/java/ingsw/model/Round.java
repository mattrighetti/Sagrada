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
            waitForMove();
            playerEndedTurn.set(true);
        });
        playerMoves.start();
    }

    public void hasMadeAMove() {
        hasMadeAMove.set(true);
    }
    private void waitForMove() {
        while (!hasMadeAMove.get()) {

        }
        hasMadeAMove.set(false);
    }

    public void setPlayerEndedTurn(boolean hasPlayerEndedTurn) {
        playerEndedTurn.set(hasPlayerEndedTurn);
        playerMoves.interrupt();
    }

    public AtomicBoolean hasPlayerEndedTurn() {
        return playerEndedTurn;
    }

    public boolean makeMove() {
        //TODO ricontrolla se deve ritornare realmente valori
        boolean isMoveAccepted = gameManager.equals("");
        hasMadeAMove.set(isMoveAccepted);
        return isMoveAccepted;
    }

    /**
     * Place-Dice move. Method for the placing dice move
     * @param dice to place
     * @param rowIndex index of the row where to place dice in the pattern card
     * @param columnIndex index of the column where to place dice in pattern card
     */
    public void makeMove(Dice dice, int rowIndex, int columnIndex) {
        if (gameManager.makeMove(player, dice, rowIndex, columnIndex)) hasMadeAMove.set(true);
    }

    public void skipMove() {
       hasMadeAMove.set(true);
    }

    public Player getCurrentPlayer() {
        return player;
    }
}

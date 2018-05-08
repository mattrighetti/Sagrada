package ingsw.model;

import java.util.concurrent.atomic.AtomicBoolean;

public class Round implements Runnable {
    private Thread playerMoves;
    private Player player;
    private AtomicBoolean hasMadeAMove;
    private GameManager gameManager;

    public Round(GameManager gameManager) {
        this.gameManager = gameManager;
        hasMadeAMove.set(false);
    }

    public void startForPlayer(Player player) {
        this.player = player;
        run();
    }

    @Override
    public void run() {
        playerMoves = new Thread( () -> {
            player.getUser().getUserObserver().activateTurnNotification();
            executeMove();
            executeMove();
            //gameManager.nextTurn();
        });
        playerMoves.start();
    }

    private void executeMove() {
        while (!hasMadeAMove.get()) {

        }
        hasMadeAMove.set(false);
    }



    public boolean makeMove() {
        boolean isMoveAccepted = gameManager.equals("");
        hasMadeAMove.set(isMoveAccepted);
        return isMoveAccepted;
    }

    public void doNotMakeAMove() {
       hasMadeAMove.set(true);
    }
}

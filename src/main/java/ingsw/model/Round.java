package ingsw.model;

import java.util.concurrent.atomic.AtomicBoolean;

public class Round implements Runnable {
    private Thread turno;
    private Player player;
    private AtomicBoolean hasMadeAMove;
    private GameManager gameManager;
/*
    public Round(GameManager gameManager) {
        this.gameManager = gameManager;
        hasMadeAMove.set(false);
    }

    public void startRoundForPlayer(Player player) {
        setPlayer(player);
        turno.run();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
*/


    @Override
    public void run() {
        //executeMove();
        //executeMove();
        //gameManager.nextTurn();
    }

/*

    private void executeMove() {
        player.getUser().updateView();
        while (!hasMadeAMove) {

        }
        hasMadeAMove.set(false);
    }

    public void makeMove() {
        hasMadeAMove.set(gameManager);
    }

    public void doNotMakeAMove() {

    }
    */
}

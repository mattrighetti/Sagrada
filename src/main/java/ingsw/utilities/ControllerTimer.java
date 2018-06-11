package ingsw.utilities;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.TimeOutResponse;
import ingsw.model.GameManager;
import ingsw.model.Round;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class ControllerTimer {
    private Timer timer;
    private static ControllerTimer controllerTimer;

    private ControllerTimer() {
        this.timer = new Timer("TimerThread");
    }

    public static ControllerTimer get() {
        if (controllerTimer == null) {
            controllerTimer = new ControllerTimer();
        }

        return controllerTimer;
    }

    public void startLoginTimer(int loginSeconds, Controller controller) {
        timer.schedule(new LaunchMatch(controller), loginSeconds * 1000);
    }

    public void startTurnTimer(int turnSeconds, GameManager gameManager) {
        timer.schedule(new EndTurn(gameManager), turnSeconds * 1000);
    }

    public void cancelTimer() {
        timer.cancel();
    }

    public class LaunchMatch extends TimerTask {
        Controller controller;

        LaunchMatch(Controller controller) {
            this.controller = controller;
        }

        @Override
        public void run() {
            controller.createMatch();
        }
    }

    class EndTurn extends TimerTask {
        GameManager gameManager;

        EndTurn(GameManager gameManager) {
            this.gameManager = gameManager;
        }

        @Override
        public void run() {

            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new TimeOutResponse());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            gameManager.endTurn();
        }
    }


}

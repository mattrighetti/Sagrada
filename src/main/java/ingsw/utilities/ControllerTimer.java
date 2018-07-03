package ingsw.utilities;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.TimeOutResponse;
import ingsw.controller.network.socket.ClientHandler;
import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerTimer {
    private AtomicBoolean pingActive;
    private Timer timer;
    private static final String TIMER_THREAD_NAME = "TimerThread";

    public ControllerTimer() {
        this.timer = new Timer(TIMER_THREAD_NAME);
        pingActive = new AtomicBoolean(false);
    }

    public AtomicBoolean getPingActive() {
        return pingActive;
    }

    public void setPingActive(boolean pingActive) {
        this.pingActive.set(pingActive);
    }

    public void startLoginTimer(int loginSeconds, Controller controller, boolean hasStarted) {
        timer = new Timer(TIMER_THREAD_NAME);
        timer.schedule(new LaunchMatch(controller, hasStarted), (long) loginSeconds * 1000);
    }

    public void startTurnTimer(int turnSeconds, GameManager gameManager) {
        timer = new Timer(TIMER_THREAD_NAME);
        timer.schedule(new EndTurn(gameManager), (long) turnSeconds * 1000);
    }

    public void startPatternCardTimer(int patternCardSeconds, GameManager gameManager, Map<String, List<PatternCard>> patternCards) {
        timer = new Timer(TIMER_THREAD_NAME);
        timer.schedule(new ChoosePatternCard(gameManager, patternCards), (long) patternCardSeconds * 1000);
    }

    public void startDraftedDiceTimer(GameManager gameManager) {
        timer = new Timer(TIMER_THREAD_NAME);
        timer.schedule(new DraftDiceTask(gameManager), (long) 20 * 1000);
    }

    public void startPingReceiveTimer(ClientHandler clientHandler) {
        if (!pingActive.get()) {
            pingActive.set(true);
            timer = new Timer(TIMER_THREAD_NAME);
            timer.schedule(new DisconnectUserTask(clientHandler), (long) 1000);
        }
    }

    /**
     * Method to stop the timer that is running
     */
    public void cancelTimer() {
        timer.cancel();
        timer.purge();
    }

    /**
     * Task class for Launch Match Timer
     */
    public class LaunchMatch extends TimerTask {
        Controller controller;
        boolean hasStarted;

        LaunchMatch(Controller controller, boolean hasStarted) {
            this.controller = controller;
            this.hasStarted = hasStarted;
        }

        @Override
        public void run() {
            controller.setStop(true);
            hasStarted = true;
            controller.createMatch();
        }
    }

    /**
     * Task class for choosing Pattern Card Timer
     */
    class ChoosePatternCard extends TimerTask {
        GameManager gameManager;
        Map<String, List<PatternCard>> patternCards;

        ChoosePatternCard(GameManager gameManager, Map<String, List<PatternCard>> patternCards) {
            this.gameManager = gameManager;
            this.patternCards = patternCards;
        }

        /**
         * When the time to choose the pattern card expires this task runs
         * It calls the gameManager method to choose randomly the pattern card for the players
         */
        @Override
        public void run() {
            gameManager.randomizePatternCards(patternCards);
        }
    }

    /**
     * Task class for draft the Dice automatically if player waits too much time
     */
    class DraftDiceTask extends TimerTask {

        GameManager gameManager;

        DraftDiceTask(GameManager gameManager) {
            this.gameManager = gameManager;
        }

        @Override
        public void run() {
            gameManager.draftDiceFromBoard();
        }
    }

    /**
     * Task class for End Turn Timer
     */
    class EndTurn extends TimerTask {
        GameManager gameManager;

        EndTurn(GameManager gameManager) {
            this.gameManager = gameManager;

        }

        @Override
        public void run() {
            //if the timer expires, execute this code

            Player currentPlayer = gameManager.getCurrentRound().getCurrentPlayer();

            try {

                if (gameManager.getToolCardLock().get())
                    currentPlayer.getUserObserver().sendResponse(new TimeOutResponse(gameManager.getDraftedDice(), gameManager.getRoundTrack(), currentPlayer));
                else
                    currentPlayer.getUserObserver().sendResponse(new TimeOutResponse());

                synchronized (this) {
                    wait(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            System.out.println("timer ended\n");

            gameManager.addMoveToHistoryAndNotify(new MoveStatus(currentPlayer.getPlayerUsername(), "ended the turn due to time out"));

            gameManager.getCurrentRound().avoidEndTurnNotification(true);

            gameManager.stopTurn();

            //otherwise do nothing

            System.out.println("Deleting timer");
        }
    }

    class DisconnectUserTask extends TimerTask {
        ClientHandler clientHandler;

        DisconnectUserTask(ClientHandler clientHandler) {
            this.clientHandler = clientHandler;
        }

        @Override
        public void run() {
            clientHandler.close();
            clientHandler.getServerController().deactivateUser();
        }
    }
}

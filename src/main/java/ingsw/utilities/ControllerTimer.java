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

/**
 * Timer used to control some game sequence like:
 * - Login
 * - Pattern card choose
 * - Drafted dice
 * - Turn execution
 */
public class ControllerTimer {
    private AtomicBoolean pingActive;
    private Timer timer;
    private static final String TIMER_THREAD_NAME = "TimerThread";

    /**
     * Creates a new ControllerTimer
     */
    public ControllerTimer() {
        this.timer = new Timer(TIMER_THREAD_NAME);
        pingActive = new AtomicBoolean(false);
    }

    /**
     * Get if the pinger is active
     * @return true if it is active
     */
    public AtomicBoolean getPingActive() {
        return pingActive;
    }

    /**
     * Set the new ping value
     * @param pingActive new Ping value
     */
    public void setPingActive(boolean pingActive) {
        this.pingActive.set(pingActive);
    }

    /**
     * Start the Login timer
     * @param loginSeconds Seconds before the match starts
     * @param controller Match controller
     */
    public void startLoginTimer(int loginSeconds, Controller controller) {
        timer = new Timer(TIMER_THREAD_NAME);
        timer.schedule(new LaunchMatch(controller), (long) loginSeconds * 1000);
    }

    /**
     * Start the Turn timer
     * @param turnSeconds Seconds before the turn ends
     * @param gameManager GameManager
     */
    public void startTurnTimer(int turnSeconds, GameManager gameManager) {
        timer = new Timer(TIMER_THREAD_NAME);
        timer.schedule(new EndTurn(gameManager), (long) turnSeconds * 1000);
    }

    /**
     * Start the Pattern card timer
     * @param patternCards Seconds before the pattern card is automatically chosen
     * @param gameManager GameManager
     */
    public void startPatternCardTimer(int patternCardSeconds, GameManager gameManager, Map<String, List<PatternCard>> patternCards) {
        timer = new Timer(TIMER_THREAD_NAME);
        timer.schedule(new ChoosePatternCard(gameManager, patternCards), (long) patternCardSeconds * 1000);
    }

    /**
     * Start the Drafted dice timer
     * @param gameManager GameManager
     */
    public void startDraftedDiceTimer(GameManager gameManager) {
        timer = new Timer(TIMER_THREAD_NAME);
        timer.schedule(new DraftDiceTask(gameManager), (long) 20 * 1000);
    }

    /**
     * Method to stop the timer that is running
     */
    public void cancelTimer() {
        timer.cancel();
        timer.purge();
    }

    public void startPingReceiveTimer(ClientHandler clientHandler) {
        if (!pingActive.get()) {
            pingActive.set(true);
            timer = new Timer(TIMER_THREAD_NAME);
            timer.schedule(new DisconnectUserTask(clientHandler), (long) 3000);
        }
    }

    /**
     * Task class for Launch Match Timer
     */
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

                if (gameManager.getToolCardLock().get()) {
                    if (currentPlayer.getUser().isActive()) {
                        currentPlayer.getUserObserver().sendResponse(new TimeOutResponse(gameManager.getDraftedDice(), gameManager.getRoundTrack(), currentPlayer));
                    }
                } else {
                    if (currentPlayer.getUser().isActive()) {
                        currentPlayer.getUserObserver().sendResponse(new TimeOutResponse());
                    }
                }

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
            System.out.print("ControllerTimer:");
            clientHandler.shutdownClientHandler();
        }
    }
}

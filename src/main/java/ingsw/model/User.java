package ingsw.model;

import ingsw.controller.network.socket.UserObserver;
import org.apache.commons.lang.time.StopWatch;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that identifies the "client" until the game started, after the game has started it will be contained in
 * the Player class
 */
public class User implements Serializable {
    private String username;
    private int positionInRanking;
    private boolean active;
    private boolean ready;
    private UserObserver userObserver;
    private int noOfWins;
    private int noOfLose;
    private List<String> matchesPlayed;
    private transient StopWatch stopWatch;
    private transient boolean isStopWatchRunning;
    private transient boolean hasStopWatchStarted;

    /**
     * Set the username, creates the stopWatch, set ready and active to true anche create a list
     * for storing the matches played
     * @param username Username to set
     */
    public User(String username) {
        active = true;
        ready = true;
        matchesPlayed = new LinkedList<>();
        this.stopWatch = new StopWatch();
        this.username = username;
        this.isStopWatchRunning = false;
        this.hasStopWatchStarted = false;
    }

    /**
     * Get the actual ranking position
     * @return Returns the position in the ranking
     */
    int getPositionInRanking() {
        return positionInRanking;
    }

    /**
     * Set the new position
     * @param positionInRanking The new ranking position
     */
    void setPositionInRanking(int positionInRanking) {
        this.positionInRanking = positionInRanking;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Returns the number of wins
     * @return Number of wins
     */
    int getNoOfWins() {
        return noOfWins;
    }

    /**
     * Increments the number of wins
     */
    void incrementNoOfWins() {
        noOfWins++;
    }

    /**
     * Returns the number of lose
     * @return Number of lose
     */
    int getNoOfLose() {
        return noOfLose;
    }

    /**
     * Increments the number of lose
     */
    void incrementNoOfLose() {
        noOfLose++;
    }

    /**
     * Returns the number of matches played
     * @return Number of matches played
     */
    List<String> getMatchesPlayed() {
        return matchesPlayed;
    }

    /**
     * Sets the current UserObserver.
     * @param userObserver UserObserver to attach
     */
    public void attachUserObserver(UserObserver userObserver) {
        this.userObserver = userObserver;
    }

    /**
     * @return Returns the UserObserver
     * @throws RemoteException Thrown in case of disconnection
     */
    public UserObserver getUserObserver() throws RemoteException {
        try {
            userObserver.checkIfActive();
        } catch (RemoteException | NullPointerException e) {
            if (isActive()) {
                setActive(false);
                setReady(false);
            }
        }
        return userObserver;
    }

    /**
     * Sets the user active(equivalent of in-game)
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns true if the user is in-game
     * @return User active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns if the user active(equivalent of online)
     * @return User ready
     */
    boolean isReady() {
        return ready;
    }

    boolean isStopWatchRunning() {
        return isStopWatchRunning;
    }

    boolean isHasStopWatchStarted() {
        return hasStopWatchStarted;
    }

    public void setReady(boolean ready) {
        if (this.ready != ready && hasStopWatchStarted) {
            System.out.println("READY E DIVERSO DAL VALORE PRECEDENTE, NON ENTRO");
            this.ready = ready;
            if (!hasStopWatchStarted && this.ready) {
                stopWatch.start();
                hasStopWatchStarted = true;
                isStopWatchRunning = true;
                System.out.println("PARTITO DIOCANE");
            } else {
                if (!isStopWatchRunning && this.ready) {
                    System.out.println("RESUME");
                    isStopWatchRunning = true;
                    stopWatch.resume();
                } else {
                    System.out.println("SUSPEND");
                    isStopWatchRunning = false;
                    stopWatch.suspend();
                }
            }
        }
    }

    /**
     * Returns the currently time played
     * @return Currently time played
     */
    private long getActiveTime() {
        return stopWatch.getTime();
    }

    /**
     * Returns time played formatted
     * @return Time played formatted
     */
    String getFormattedTime() {
        Date formattedTimeActive = new Date(getActiveTime());
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(formattedTimeActive);
    }

    @Override
    public String toString() {
        return getUsername();
    }
}

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

    public User(String username) {
        active = true;
        ready = true;
        matchesPlayed = new LinkedList<>();
        this.stopWatch = new StopWatch();
        this.username = username;
        this.isStopWatchRunning = false;
    }

    int getPositionInRanking() {
        return positionInRanking;
    }

    void setPositionInRanking(int positionInRanking) {
        this.positionInRanking = positionInRanking;
    }

    public String getUsername() {
        return username;
    }

    int getNoOfWins() {
        return noOfWins;
    }

    void incrementNoOfWins() {
        noOfWins++;
    }

    int getNoOfLose() {
        return noOfLose;
    }

    void incrementNoOfLose() {
        noOfLose++;
    }

    List<String> getMatchesPlayed() {
        return matchesPlayed;
    }

    public void attachUserObserver(UserObserver userObserver) {
        this.userObserver = userObserver;
    }

    public UserObserver getUserObserver() throws RemoteException {
        userObserver.checkIfActive();
        return userObserver;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
        if (ready) {
            isStopWatchRunning = true;
            stopWatch.start();
        }
        else {
            if (isStopWatchRunning) {
                isStopWatchRunning = false;
                stopWatch.suspend();
            }
        }
    }

    private long getActiveTime() {
        return stopWatch.getTime();
    }

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

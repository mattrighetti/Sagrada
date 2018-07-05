package ingsw.model;

import ingsw.controller.network.socket.UserObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Matt");
    }

    @Test
    void getUsername() {
        assertEquals("Matt", user.getUsername());
    }

    @Test
    void getNoOfWins() {
        assertTrue(user.getNoOfWins() >= 0);
    }

    @Test
    void getNoOfLose() {
        assertTrue(user.getNoOfLose() >= 0);
    }

    @Test
    void getMatchesPlayed() {
        assertNotNull(user.getMatchesPlayed());
    }

    @Test
    void getFormattedTimeTest() {
        user.getFormattedTime();
    }

    @Test
    void readyTest() {
        user.setReady(true);
        assertTrue(user.isReady());
        assertFalse(user.isHasStopWatchStarted());
        assertFalse(user.isStopWatchRunning());
        user.setReady(false);
        assertFalse(user.isReady());
        assertFalse(user.isHasStopWatchStarted());
        assertFalse(user.isStopWatchRunning());
        user.setReady(false);
        user.setActive(true);
        assertTrue(user.isActive());
        assertTrue(user.isHasStopWatchStarted());
        assertTrue(user.isStopWatchRunning());
        user.setActive(true);
        assertTrue(user.isActive());
        assertTrue(user.isHasStopWatchStarted());
        assertTrue(user.isStopWatchRunning());
        user.setActive(false);
        assertTrue(user.isHasStopWatchStarted());
        assertFalse(user.isStopWatchRunning());
        user.setActive(false);
        assertTrue(user.isHasStopWatchStarted());
        assertFalse(user.isStopWatchRunning());
        user.setActive(true);
        assertTrue(user.isHasStopWatchStarted());
        assertTrue(user.isStopWatchRunning());

    }

    @Test
    void activeTest() {
        user.setActive(true);
        assertTrue(user.isActive());
        user.setActive(false);
        assertFalse(user.isActive());
    }

    @Test
    void attachUserObserverTest() throws RemoteException {
        user.attachUserObserver(Mockito.mock(UserObserver.class));
        assertNotNull(user.getUserObserver());
    }

    @Test
    void incrementNoOfLose() {
        user.incrementNoOfLose();
        assertEquals(1, user.getNoOfLose());
    }

    @Test
    void incrementNoOfWins() {
        user.incrementNoOfWins();
        assertEquals(1, user.getNoOfWins());
    }

    @Test
    void getPositionInRankingTest() {
        user.setPositionInRanking(2);
        assertEquals(2, user.getPositionInRanking());
    }
}
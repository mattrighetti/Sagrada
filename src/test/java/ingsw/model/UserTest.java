package ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User("Matt");
    }

    @Test
    void getUsername() {
        assertTrue(user.getUsername().equals("Matt"));
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
    void getNoOfDraws() {
        assertTrue(user.getNoOfDraws() >= 0);
    }

    @Test
    void getMatchesPlayed() {
        assertNotNull(user.getMatchesPlayed());
    }
}
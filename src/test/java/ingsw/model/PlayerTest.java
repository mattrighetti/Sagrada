package ingsw.model;

import ingsw.view.RemoteView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setUp() {
        User user = new User("Matt");
        player = new Player(user);
    }

    @Test
    void getUser() throws NoSuchFieldException {
        assertNotNull(player.getUser());
        assertEquals("Matt", player.getUser().getUsername());
    }
}
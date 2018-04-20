package ingsw.model;

import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setUp() {
        User user = new User("Matt");
        player = new Player(user, new PrivateObjectiveCard(Color.PURPLE));
    }

    @Test
    void getUser() throws NoSuchFieldException {
        assertNotNull(player.getUser());
        assertNotNull(player.getClass().getDeclaredField("privateObjectiveCard"));
        assertEquals("Matt", player.getUser().getUsername());
    }
}
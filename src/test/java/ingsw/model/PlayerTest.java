package ingsw.model;

import ingsw.controller.network.commands.Response;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.cards.patterncard.AuroraSagradis;
import ingsw.model.cards.patterncard.AuroraeMagnificus;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @Test
    void getScore() {
        player.setScore(1);

        assertEquals(1,player.getScore());
    }

    @Test
    void getPatternCard() {
        AuroraSagradis auroraSagradis = new AuroraSagradis();
        player.setPatternCard(auroraSagradis);

        assertEquals(auroraSagradis, player.getPatternCard());
    }

    @Test
    void getPrivateObjectiveCard() {
        PrivateObjectiveCard privateObjectiveCard = new PrivateObjectiveCard(Color.BLUE);
        player.setPrivateObjectiveCard(privateObjectiveCard);

        assertEquals(privateObjectiveCard, player.getPrivateObjectiveCard());
    }

    @Test
    void getPlayerUsername() {
        assertEquals("Matt",player.getPlayerUsername());
    }

    @Test
    void decreaseFavourToken() {
        PatternCard patternCard = new AuroraeMagnificus();
        player.setPatternCard(patternCard);

        assertEquals(patternCard.getDifficulty(),player.getFavourTokens());

        player.decreaseFavorTokens(1);

        assertEquals(patternCard.getDifficulty() - 1,player.getFavourTokens());
    }

    @Test
    void sendResponse() {
        player.getUser().attachUserObserver(mock(UserObserver.class));

        player.sendResponse(mock(Response.class));

    }
}
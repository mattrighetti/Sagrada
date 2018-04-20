package ingsw.model.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card card;

    @BeforeEach
    void setUp() {
        card = new Card("TestCard") {

        };
    }

    @Test
    void toStringTest() {
        assertEquals("Card{'TestCard'}", card.toString());
    }

    @Test
    void getName() {
        assertEquals("TestCard", card.getName());
    }
}
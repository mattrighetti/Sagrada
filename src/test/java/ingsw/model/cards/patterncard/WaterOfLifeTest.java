package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaterOfLifeTest {
    WaterOfLife waterOfLife;

    @BeforeEach
    void setUp(){
        waterOfLife = new WaterOfLife();
    }

    @Test
    void toStringTest() {
        assertEquals("WaterOfLife", waterOfLife.getName());
        assertEquals("PatternCard{'WaterOfLife'}", waterOfLife.toString());
    }
}
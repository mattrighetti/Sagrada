package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalsTest {
    Diagonals diagonals;

    @BeforeEach
    void setUp() {
        diagonals = new Diagonals();
    }

    @Test
    void toStringTest() {
        assertEquals("Diagonals", diagonals.getName());
        assertEquals("PublicObjCard{'Diagonals'}", diagonals.toString());
    }

    @Test
    void checkTest() {
        diagonals.check();
        //TODO test
    }

}
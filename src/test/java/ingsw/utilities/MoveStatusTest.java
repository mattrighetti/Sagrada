package ingsw.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveStatusTest {
    MoveStatus moveStatus;

    @BeforeEach
    void setUp() {
        moveStatus = new MoveStatus("Username", "Move");
    }

    @Test
    void getUsername() {
        assertEquals("Username", moveStatus.getUsername());
    }

    @Test
    void getMove() {
        assertEquals("Move", moveStatus.getMove());
    }

    @Test
    void getStatus() {
        assertEquals("Username Move", moveStatus.getStatus());
    }

    @Test
    void toStringTest() {
        assertEquals(" - Username Move\n", moveStatus.toString());
    }
}
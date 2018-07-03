package ingsw.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TupleTest {
    Tuple tuple;

    @BeforeEach
    void setUp() {
        tuple = new Tuple(10, 20);
    }

    @Test
    void setFirst() {
        tuple.setFirst(20);
        assertEquals(20, tuple.getFirst());
    }

    @Test
    void getFirst() {
        assertEquals(10, tuple.getFirst());
    }

    @Test
    void getSecond() {
        assertEquals(20, tuple.getSecond());
    }
}
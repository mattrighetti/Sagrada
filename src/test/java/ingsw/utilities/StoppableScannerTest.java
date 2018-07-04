package ingsw.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoppableScannerTest {
    private StoppableScanner stoppableScanner;

    @BeforeEach
    void setUp() {
        stoppableScanner = new StoppableScanner();
    }

    @Test
    void cancel() {
        stoppableScanner.cancel();
        assertTrue(stoppableScanner.isReaderCancelled());
    }
}
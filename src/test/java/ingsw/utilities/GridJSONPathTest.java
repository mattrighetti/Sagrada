package ingsw.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridJSONPathTest {
    GridJSONPath path;

    @BeforeEach
    void setUp() {
        path = GridJSONPath.AURORAE_MAGNIFICUS;
    }

    @Test
    void toStringTest() {
        assertEquals("/patterncards-json/AuroraeMagnificus.json", path.toString());
    }
}
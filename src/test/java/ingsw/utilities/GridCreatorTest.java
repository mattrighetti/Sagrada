package ingsw.utilities;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.AuroraeMagnificus;
import ingsw.model.cards.patterncard.Box;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GridCreatorTest {
    private List<List<Box>> expectedGrid;
    private List<List<Box>> actualGrid;
    private List<List<Box>> secondActualGrid;

    @BeforeEach
    void setUp() {
        expectedGrid = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            expectedGrid.add(new ArrayList<>());
        }
        expectedGrid.get(0).add(new Box(5));
        expectedGrid.get(0).add(new Box(Color.GREEN));
        expectedGrid.get(0).add(new Box(Color.BLUE));
        expectedGrid.get(0).add(new Box(Color.PURPLE));
        expectedGrid.get(0).add(new Box(2));
        expectedGrid.get(1).add(new Box(Color.PURPLE));
        expectedGrid.get(1).add(new Box(Color.BLANK));
        expectedGrid.get(1).add(new Box(Color.BLANK));
        expectedGrid.get(1).add(new Box(Color.BLANK));
        expectedGrid.get(1).add(new Box(Color.YELLOW));
        expectedGrid.get(2).add(new Box(Color.YELLOW));
        expectedGrid.get(2).add(new Box(Color.BLANK));
        expectedGrid.get(2).add(new Box(6));
        expectedGrid.get(2).add(new Box(Color.BLANK));
        expectedGrid.get(2).add(new Box(Color.PURPLE));
        expectedGrid.get(3).add(new Box(1));
        expectedGrid.get(3).add(new Box(Color.BLANK));
        expectedGrid.get(3).add(new Box(Color.BLANK));
        expectedGrid.get(3).add(new Box(Color.GREEN));
        expectedGrid.get(3).add(new Box(4));
        actualGrid = new AuroraeMagnificus().getGrid();
        secondActualGrid = new AuroraeMagnificus().getGrid();
    }

    @Test
    void fromString() {
        assertEquals(expectedGrid, actualGrid);
        assertEquals(actualGrid, secondActualGrid);
    }
}
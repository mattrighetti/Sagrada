package ingsw.utilities;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.AuroraSagradis;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.toolcards.ToolCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridCreatorTest {
    List<List<Box>> expectedGrid;
    List<List<Box>> actualGrid;
    List<List<Box>> secondActualGrid;

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
        actualGrid = GridCreator.fromFile(GridJSONPath.AURORAE_MAGNIFICUS);
        secondActualGrid = GridCreator.fromFile(GridJSONPath.AURORAE_MAGNIFICUS);
    }

    @Test
    void fromFile() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (expectedGrid.get(i).get(j).isValueSet()) {
                    assertEquals(expectedGrid.get(i).get(j).getValue(), actualGrid.get(i).get(j).getValue(),
                                 "GetValue Box[" + i + "][" + j + "]");
                    assertEquals(expectedGrid.get(i).get(j).getValue(), secondActualGrid.get(i).get(j).getValue(),
                                 "GetValue Box[" + i + "][" + j + "]");
                } else {
                    assertEquals(expectedGrid.get(i).get(j).getColor(), actualGrid.get(i).get(j).getColor(),
                            "GetColor at Box[" + i + "][" + j + "]");
                    assertEquals(expectedGrid.get(i).get(j).getColor(), secondActualGrid.get(i).get(j).getColor(),
                                 "GetColor at Box[" + i + "][" + j + "]");
                    assertNull(expectedGrid.get(i).get(j).getValue());
                }
            }
        }
    }

    @Test
    void multipleReads() {
        PatternCard patternCard = new AuroraSagradis();
        PatternCard patternCard1 = new AuroraSagradis();
    }
}
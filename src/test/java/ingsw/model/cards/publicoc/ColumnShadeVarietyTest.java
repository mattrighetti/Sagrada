package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.Box;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColumnShadeVarietyTest {
    ColumnShadeVariety columnShadeVariety;
    List<List<Box>> grid;
    Dice dice2, dice1;

    @BeforeEach
    void setUp() {
        columnShadeVariety = new ColumnShadeVariety();
        dice1 = new Dice(Color.BLUE);
        dice1.setFaceUpValue(5);
        dice2 = new Dice(Color.GREEN);
        dice2.setFaceUpValue(3);
        grid = new ArrayList<>();
        grid.add(new ArrayList<>());
        grid.add(new ArrayList<>());
        grid.add(new ArrayList<>());
        grid.add(new ArrayList<>());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                grid.get(i).add(j, new Box(Color.BLANK));
            }
        }
        grid.get(0).get(0).insertDice(dice1);
        grid.get(3).get(1).insertDice(dice2);
    }

    @Test
    void toStringTest() {
        assertEquals("ColumnShadeVariety", columnShadeVariety.getName());
        assertEquals("PublicObjCard{'ColumnShadeVariety'}", columnShadeVariety.toString());
    }

    @Test
    void checkTest() {
        assertNotEquals(1, columnShadeVariety.check(grid));
    }
}
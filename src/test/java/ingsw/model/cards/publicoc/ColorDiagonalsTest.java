package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.Box;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorDiagonalsTest {
    ColorDiagonals colorDiagonals;

    @BeforeEach
    void setUp() {
        colorDiagonals = new ColorDiagonals();
    }

    @Test
    void toStringTest() {
        assertEquals("ColorDiagonals", colorDiagonals.getName());
        assertEquals("PublicObjCard{'ColorDiagonals'}", colorDiagonals.toString());
    }

    @Test
    void checkTest() {
        ArrayList<List<Box>> grid;

        grid = new ArrayList<List<Box>>(4);
        grid.add(new ArrayList<>(5));
        grid.add(new ArrayList<>(5));
        grid.add(new ArrayList<>(5));
        grid.add(new ArrayList<>(5));


        for(int i = 0; i < 4; i++){
            for(int j = 0; j <5; j++){
                grid.get(i).add(j, new Box(Color.BLANK));
            }
        }

        //place 4 dice in diagonals

        grid.get(0).get(3).insertDice(new Dice(Color.BLUE));
        grid.get(1).get(2).insertDice(new Dice(Color.BLUE));
        grid.get(2).get(1).insertDice(new Dice(Color.BLUE));
        grid.get(3).get(0).insertDice(new Dice(Color.BLUE));
        grid.get(0).get(0).insertDice(new Dice(Color.BLUE));
        grid.get(1).get(1).insertDice(new Dice(Color.BLUE));
        grid.get(2).get(2).insertDice(new Dice(Color.BLUE));
        grid.get(3).get(3).insertDice(new Dice(Color.BLUE));




        assertEquals(4,colorDiagonals.check(grid));
    }

}
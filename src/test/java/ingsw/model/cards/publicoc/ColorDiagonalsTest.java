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
    private ColorDiagonals colorDiagonals;
    private List<List<Box>> gridOne;
    private List<List<Box>> gridTwo;
    private List<List<Box>> gridThree;
    private List<List<Box>> gridFour;
    private List<List<Box>> gridFive;
    private List<List<Box>> gridSix;


    @BeforeEach
    void setUp() {

        colorDiagonals = new ColorDiagonals();

        gridOne = new ArrayList<>(4);
        gridOne.add(new ArrayList<>(5));
        gridOne.add(new ArrayList<>(5));
        gridOne.add(new ArrayList<>(5));
        gridOne.add(new ArrayList<>(5));

        gridTwo = new ArrayList<>(4);
        gridTwo.add(new ArrayList<>(5));
        gridTwo.add(new ArrayList<>(5));
        gridTwo.add(new ArrayList<>(5));
        gridTwo.add(new ArrayList<>(5));

        gridThree = new ArrayList<>(4);
        gridThree.add(new ArrayList<>(5));
        gridThree.add(new ArrayList<>(5));
        gridThree.add(new ArrayList<>(5));
        gridThree.add(new ArrayList<>(5));

        gridFour = new ArrayList<>(4);
        gridFour.add(new ArrayList<>(5));
        gridFour.add(new ArrayList<>(5));
        gridFour.add(new ArrayList<>(5));
        gridFour.add(new ArrayList<>(5));

        gridFive = new ArrayList<>(4);
        gridFive.add(new ArrayList<>(5));
        gridFive.add(new ArrayList<>(5));
        gridFive.add(new ArrayList<>(5));
        gridFive.add(new ArrayList<>(5));

        gridSix = new ArrayList<>(4);
        gridSix.add(new ArrayList<>(5));
        gridSix.add(new ArrayList<>(5));
        gridSix.add(new ArrayList<>(5));
        gridSix.add(new ArrayList<>(5));

        for(int i = 0; i < 4; i++){
            for(int j = 0; j <5; j++){
                gridOne.get(i).add(j, new Box(Color.BLANK));
                gridTwo.get(i).add(j, new Box(Color.BLANK));
                gridThree.get(i).add(j, new Box(Color.BLANK));
                gridFour.get(i).add(j, new Box(Color.BLANK));
                gridFive.get(i).add(j, new Box(Color.BLANK));
                gridSix.get(i).add(j, new Box(Color.BLANK));

            }
        }

        gridOne.get(0).get(3).insertDice(new Dice(Color.BLUE));
        gridOne.get(1).get(2).insertDice(new Dice(Color.BLUE));
        gridOne.get(2).get(1).insertDice(new Dice(Color.BLUE));
        gridOne.get(3).get(0).insertDice(new Dice(Color.BLUE));
        gridOne.get(0).get(0).insertDice(new Dice(Color.BLUE));
        gridOne.get(1).get(1).insertDice(new Dice(Color.BLUE));
        gridOne.get(2).get(2).insertDice(new Dice(Color.BLUE));
        gridOne.get(3).get(3).insertDice(new Dice(Color.BLUE));

        gridTwo.get(0).get(2).insertDice(new Dice(Color.RED));
        gridTwo.get(1).get(1).insertDice(new Dice(Color.RED));
        gridTwo.get(1).get(3).insertDice(new Dice(Color.RED));
        gridTwo.get(2).get(2).insertDice(new Dice(Color.RED));

        gridThree.get(0).get(2).insertDice(new Dice(Color.RED));
        gridThree.get(1).get(1).insertDice(new Dice(Color.RED));
        gridThree.get(1).get(3).insertDice(new Dice(Color.RED));
        gridThree.get(2).get(2).insertDice(new Dice(Color.RED));
        gridThree.get(0).get(1).insertDice(new Dice(Color.BLUE));
        gridThree.get(1).get(2).insertDice(new Dice(Color.BLUE));
        gridThree.get(3).get(0).insertDice(new Dice(Color.GREEN));
        gridThree.get(2).get(1).insertDice(new Dice(Color.GREEN));
        gridThree.get(3).get(2).insertDice(new Dice(Color.GREEN));

        gridFive.get(0).get(0).insertDice(new Dice(Color.BLUE));
        gridFive.get(0).get(1).insertDice(new Dice(Color.BLUE));
        gridFive.get(0).get(2).insertDice(new Dice(Color.BLUE));
        gridFive.get(0).get(3).insertDice(new Dice(Color.BLUE));
        gridFive.get(0).get(4).insertDice(new Dice(Color.BLUE));
        gridFive.get(1).get(0).insertDice(new Dice(Color.BLUE));
        gridFive.get(1).get(1).insertDice(new Dice(Color.BLUE));
        gridFive.get(1).get(2).insertDice(new Dice(Color.BLUE));
        gridFive.get(1).get(3).insertDice(new Dice(Color.BLUE));
        gridFive.get(1).get(4).insertDice(new Dice(Color.BLUE));
        gridFive.get(2).get(0).insertDice(new Dice(Color.BLUE));
        gridFive.get(2).get(1).insertDice(new Dice(Color.BLUE));
        gridFive.get(2).get(2).insertDice(new Dice(Color.BLUE));
        gridFive.get(2).get(3).insertDice(new Dice(Color.BLUE));
        gridFive.get(2).get(4).insertDice(new Dice(Color.BLUE));
        gridFive.get(3).get(0).insertDice(new Dice(Color.BLUE));
        gridFive.get(3).get(1).insertDice(new Dice(Color.BLUE));
        gridFive.get(3).get(2).insertDice(new Dice(Color.BLUE));
        gridFive.get(3).get(3).insertDice(new Dice(Color.BLUE));
        gridFive.get(3).get(4).insertDice(new Dice(Color.BLUE));

        gridSix.get(0).get(2).insertDice(new Dice(Color.BLUE));
        gridSix.get(1).get(3).insertDice(new Dice(Color.BLUE));
        gridSix.get(2).get(4).insertDice(new Dice(Color.BLUE));
        gridSix.get(3).get(0).insertDice(new Dice(Color.RED));
        gridSix.get(2).get(1).insertDice(new Dice(Color.RED));
        gridSix.get(0).get(0).insertDice(new Dice(Color.RED));
        gridSix.get(0).get(1).insertDice(new Dice(Color.BLUE));
        gridSix.get(0).get(2).insertDice(new Dice(Color.BLUE));
        gridSix.get(0).get(3).insertDice(new Dice(Color.BLUE));
    }

    @Test
    void toStringTest() {
        assertEquals("ColorDiagonals", colorDiagonals.getName());
        assertEquals("PublicObjCard{'ColorDiagonals'}", colorDiagonals.toString());
    }

    @Test
    void checkTest() {
        assertEquals(8,colorDiagonals.check(gridOne));
        assertEquals(4,colorDiagonals.check(gridTwo));
        assertEquals(9,colorDiagonals.check(gridThree));
        assertEquals(0, colorDiagonals.check(gridFour));
        assertEquals(20, colorDiagonals.check(gridFive));
        assertEquals(5,colorDiagonals.check(gridSix));
    }

    @Test
    void getScoreTest() {
        assertEquals(40,colorDiagonals.getScore(gridOne));
        assertEquals(20,colorDiagonals.getScore(gridTwo));
        assertEquals(45,colorDiagonals.getScore(gridThree));
        assertEquals(0, colorDiagonals.getScore(gridFour));
        assertEquals(100, colorDiagonals.getScore(gridFive));
        assertEquals(25,colorDiagonals.getScore(gridSix));
    }

}
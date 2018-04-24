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
    ArrayList<List<Box>> gridOne;
    ArrayList<List<Box>> gridTwo;
    ArrayList<List<Box>> gridThree;
    ArrayList<List<Box>> gridFour;
    ArrayList<List<Box>> gridFive;


    @BeforeEach
    void setUp() {

        colorDiagonals = new ColorDiagonals();

        gridOne = new ArrayList<List<Box>>(4);
        gridOne.add(new ArrayList<>(5));
        gridOne.add(new ArrayList<>(5));
        gridOne.add(new ArrayList<>(5));
        gridOne.add(new ArrayList<>(5));

        gridTwo = new ArrayList<List<Box>>(4);
        gridTwo.add(new ArrayList<>(5));
        gridTwo.add(new ArrayList<>(5));
        gridTwo.add(new ArrayList<>(5));
        gridTwo.add(new ArrayList<>(5));

        gridThree = new ArrayList<List<Box>>(4);
        gridThree.add(new ArrayList<>(5));
        gridThree.add(new ArrayList<>(5));
        gridThree.add(new ArrayList<>(5));
        gridThree.add(new ArrayList<>(5));

        gridFour = new ArrayList<List<Box>>(4);
        gridFour.add(new ArrayList<>(5));
        gridFour.add(new ArrayList<>(5));
        gridFour.add(new ArrayList<>(5));
        gridFour.add(new ArrayList<>(5));

        gridFive = new ArrayList<List<Box>>(4);
        gridFive.add(new ArrayList<>(5));
        gridFive.add(new ArrayList<>(5));
        gridFive.add(new ArrayList<>(5));
        gridFive.add(new ArrayList<>(5));


        for(int i = 0; i < 4; i++){
            for(int j = 0; j <5; j++){
                gridOne.get(i).add(j, new Box(Color.BLANK));
            }
        }


        for(int i = 0; i < 4; i++){
            for(int j = 0; j <5; j++){
                gridTwo.get(i).add(j, new Box(Color.BLANK));
            }
        }


        for(int i = 0; i < 4; i++){
            for(int j = 0; j <5; j++){
                gridThree.get(i).add(j, new Box(Color.BLANK));
            }
        }

        for(int i = 0; i < 4; i++){
            for(int j = 0; j <5; j++){
                gridFour.get(i).add(j, new Box(Color.BLANK));
            }
        }

        for(int i = 0; i < 4; i++){
            for(int j = 0; j <5; j++){
                gridFive.get(i).add(j, new Box(Color.BLANK));
            }
        }
        //place 4 dice in diagonals

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
    }

}
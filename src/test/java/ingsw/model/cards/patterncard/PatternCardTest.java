package ingsw.model.cards.patterncard;

import ingsw.model.Color;
import ingsw.model.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatternCardTest {

    private PatternCard patternCard;

    @Test
    void computeAvailablePositionsBattloEmpty() {
        patternCard = new Batllo();
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(Color.RED);
        dice.setFaceUpValue(4);
        List<Boolean[][]> resultList;

        //empty grid test
        diceList.add(dice);
        resultList = patternCard.computeAvailablePositions(diceList);

        Boolean[][] resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                resultGrid[i][j] = false;
            }
        }
        resultGrid[0][0] = true;
        resultGrid[0][1] = true;
        resultGrid[0][3] = true;
        resultGrid[0][4] = true;
        resultGrid[1][0] = true;
        resultGrid[1][4] = true;
        resultGrid[3][1] = true;
        resultGrid[3][2] = true;


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(resultGrid[i][j], resultList.get(0)[i][j]);
            }
        }
    }


    @Test
    void computeAvailablePositionsBattlo() {
        patternCard = new Batllo();
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(Color.RED);
        dice.setFaceUpValue(4);
        List<Boolean[][]> resultList;

        //empty grid test
        diceList.add(dice);

        Boolean[][] resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                resultGrid[i][j] = false;
            }
        }

        dice = new Dice(Color.YELLOW);
        dice.setFaceUpValue(2);
        patternCard.grid.get(2).get(4).insertDice(dice);

        dice = new Dice(Color.PURPLE);
        dice.setFaceUpValue(4);
        patternCard.grid.get(2).get(3).insertDice(dice);

        resultList = patternCard.computeAvailablePositions(diceList);

        resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                resultGrid[i][j] = false;
            }
        }
        resultGrid[1][3] = true;
        resultGrid[1][4] = true;
        resultGrid[3][2] = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(resultGrid[i][j], resultList.get(0)[i][j]);
            }
        }

    }

    @Test
    void computeAvailablePositionsShadowThiefEmpty() {
        patternCard = new ShadowThief();
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(Color.PURPLE);
        dice.setFaceUpValue(5);
        List<Boolean[][]> resultList;

        //empty grid test
        diceList.add(dice);
        resultList = patternCard.computeAvailablePositions(diceList);

        Boolean[][] resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                resultGrid[i][j] = false;
            }
        }
        resultGrid[0][1] = true;
        resultGrid[0][2] = true;
        resultGrid[0][3] = true;
        resultGrid[0][4] = true;
        resultGrid[1][0] = true;
        resultGrid[1][4] = true;
        resultGrid[2][4] = true;
        resultGrid[3][2] = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(resultGrid[i][j], resultList.get(0)[i][j]);
            }
        }
    }
}
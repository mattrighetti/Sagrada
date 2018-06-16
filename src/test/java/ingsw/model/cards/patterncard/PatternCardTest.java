package ingsw.model.cards.patterncard;

import ingsw.model.Color;
import ingsw.model.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PatternCardTest {

    private PatternCard patternCard;

    @Test
    void computeAvailablePositionsBattloEmpty() {
        patternCard = new Batllo();
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(Color.RED);
        dice.setFaceUpValue(4);
        Map<String,Boolean[][]> resultMap;

        //empty grid test
        diceList.add(dice);
        resultMap = patternCard.computeAvailablePositionsDraftedDice(diceList);

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
                assertEquals(resultGrid[i][j], resultMap.get(diceList.get(0).toString())[i][j]);
            }
        }
    }


    @Test
    void computeAvailablePositionsBattlo() {
        patternCard = new Batllo();
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(Color.BLUE);
        dice.setFaceUpValue(4);
        Map<String,Boolean[][]> resultMap;

        //empty grid test
        diceList.add(dice);

        Boolean[][] resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                resultGrid[i][j] = false;
            }
        }

        dice = new Dice(Color.YELLOW);
        dice.setFaceUpValue(5);
        patternCard.grid.get(1).get(1).insertDice(dice);

        dice = new Dice(Color.YELLOW);
        dice.setFaceUpValue(4);
        patternCard.grid.get(1).get(3).insertDice(dice);


        dice = new Dice(Color.YELLOW);
        dice.setFaceUpValue(2);
        patternCard.grid.get(2).get(4).insertDice(dice);

        dice = new Dice(Color.PURPLE);
        dice.setFaceUpValue(3);
        patternCard.grid.get(2).get(3).insertDice(dice);

        resultMap = patternCard.computeAvailablePositionsDraftedDice(diceList);

        resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                resultGrid[i][j] = false;
            }
        }
        resultGrid[0][0] = true;
        resultGrid[0][1] = true;
        resultGrid[0][4] = true;
        resultGrid[1][0] = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(resultGrid[i][j], resultMap.get(diceList.get(0).toString())[i][j]);
            }
        }

    }


    @Test
    void computeAvailablePositionsKaleidoscopicDreamEmpty() {
        patternCard = new KaleidoscopicDream();
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(Color.PURPLE);
        dice.setFaceUpValue(5);
        Map<String,Boolean[][]> resultMap;

        //empty grid test
        diceList.add(dice);
        resultMap = patternCard.computeAvailablePositionsDraftedDice(diceList);

        Boolean[][] resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                resultGrid[i][j] = false;
            }
        }
        resultGrid[0][2] = true;
        resultGrid[0][3] = true;
        resultGrid[3][1] = true;
        resultGrid[3][2] = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(resultGrid[i][j], resultMap.get(diceList.get(0).toString())[i][j]);
            }
        }
    }


    @Test
    void computeAvailablePositionsKaleidoscopicDream() {
        patternCard = new KaleidoscopicDream();
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(Color.PURPLE);
        dice.setFaceUpValue(5);
        Map<String,Boolean[][]> resultMap;

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
        patternCard.grid.get(3).get(4).insertDice(dice);

        dice = new Dice(Color.BLUE);
        dice.setFaceUpValue(3);
        patternCard.grid.get(3).get(3).insertDice(dice);

        dice = new Dice(Color.GREEN);
        dice.setFaceUpValue(2);
        patternCard.grid.get(3).get(0).insertDice(dice);

        dice = new Dice(Color.RED);
        dice.setFaceUpValue(3);
        patternCard.grid.get(3).get(1).insertDice(dice);

        dice = new Dice(Color.RED);
        dice.setFaceUpValue(4);
        patternCard.grid.get(2).get(3).insertDice(dice);

        dice = new Dice(Color.YELLOW);
        dice.setFaceUpValue(2);
        patternCard.grid.get(1).get(3).insertDice(dice);

        dice = new Dice(Color.BLUE);
        dice.setFaceUpValue(3);
        patternCard.grid.get(1).get(1).insertDice(dice);

        resultMap = patternCard.computeAvailablePositionsDraftedDice(diceList);

        resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                resultGrid[i][j] = false;
            }
        }

        resultGrid[0][2] = true;
        resultGrid[0][3] = true;
        resultGrid[1][2] = true;
        resultGrid[3][2] = true;
        resultGrid[2][1] = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(resultGrid[i][j], resultMap.get(diceList.get(0).toString())[i][j]);
            }
        }

    }



    @Test
    void computeAvailablePositionsShadowThiefEmpty() {
        patternCard = new ShadowThief();
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(Color.PURPLE);
        dice.setFaceUpValue(5);
        Map<String,Boolean[][]> resultMap;

        //empty grid test
        diceList.add(dice);
        resultMap = patternCard.computeAvailablePositionsDraftedDice(diceList);

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
                assertEquals(resultGrid[i][j], resultMap.get(diceList.get(0).toString())[i][j]);
            }
        }
    }


    @Test
    void computeAvailablePositionsShadowThiefFull() {
        patternCard = new ShadowThief();
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(Color.PURPLE);
        dice.setFaceUpValue(5);
        Map<String,Boolean[][]> resultMap;

        Boolean[][] resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                patternCard.getGrid().get(i).get(j).insertDice(new Dice(Color.PURPLE));
                resultGrid[i][j] = false;
            }
        }
        //empty grid test
        diceList.add(dice);
        resultMap = patternCard.computeAvailablePositionsDraftedDice(diceList);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(resultGrid[i][j], resultMap.get(diceList.get(0).toString())[i][j]);
            }
        }
    }

    @Test
    void computeAvailablePositionsNoDiceAround() {
        patternCard = new ShadowThief();
        List<Dice> diceList = new ArrayList<>();
        Map<String,Boolean[][]> resultMap;


        Dice dice = new Dice(Color.PURPLE);
        dice.setFaceUpValue(5);
        patternCard.grid.get(0).get(3).insertDice(dice);

        dice = new Dice(Color.PURPLE);
        dice.setFaceUpValue(4);
        patternCard.grid.get(1).get(2).insertDice(dice);

        dice = new Dice(Color.YELLOW);
        dice.setFaceUpValue(5);
        patternCard.grid.get(1).get(4).insertDice(dice);

        dice = new Dice(Color.YELLOW);
        dice.setFaceUpValue(4);
        patternCard.grid.get(3).get(3).insertDice(dice);

        dice = new Dice(Color.RED);
        dice.setFaceUpValue(3);
        diceList.add(dice);
        resultMap = patternCard.computeAvailablePositionsNoDiceAround(diceList);

        Boolean[][] resultGrid = new Boolean[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                resultGrid[i][j] = false;
            }
        }
        resultGrid[2][0] = true;
        resultGrid[3][1] = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(resultGrid[i][j], resultMap.get(diceList.get(0).toString())[i][j]);
            }
        }
    }
}
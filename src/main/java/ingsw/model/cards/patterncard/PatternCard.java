package ingsw.model.cards.patterncard;

import ingsw.model.Color;
import ingsw.model.Dice;
import ingsw.model.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PatternCard extends Card {
    private int difficulty;
    protected List<List<Box>> grid;

    public PatternCard(String name, int difficulty) {
        super(name);
        fillGrid();
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "PatternCard{" +
                "'" + getName() + "'" +
                '}';
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setGrid(List<List<Box>> grid) {
        this.grid = grid;
    }

    public List<List<Box>> getGrid() {
        return grid;
    }

    private void fillGrid() {
        this.grid = new ArrayList<>(4);
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
    }

    public Map<String,Boolean[][]> computeAvailablePositions(List<Dice> draftedDice) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        for (Dice dice : draftedDice) {
            if(!hashMapGrid.containsKey(dice.toString())) {
                hashMapGrid.put(dice.toString(), computePosition(dice, true, true,true));
            }
        }
        return hashMapGrid;
    }

    public Map<String,Boolean[][]> computeAvailablePositionsNoValue(List<Dice> draftedDice) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        for (Dice dice : draftedDice) {
            if(!hashMapGrid.containsKey(dice.toString())) {
                hashMapGrid.put(dice.toString(), computePosition(dice, true, false,true));
            }
        }
        return hashMapGrid;
    }

    public Map<String,Boolean[][]> computeAvailablePositionsNoColor(List<Dice> draftedDice) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        for (Dice dice : draftedDice) {
            if(!hashMapGrid.containsKey(dice.toString())) {
                hashMapGrid.put(dice.toString(), computePosition(dice, false, true,true));
            }
        }
        return hashMapGrid;
    }

    public Map<String,Boolean[][]> computeAvailablePositionsNoDiceAround(List<Dice> draftedDice) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        for (Dice dice : draftedDice) {
            if(!hashMapGrid.containsKey(dice.toString())) {
                hashMapGrid.put(dice.toString(), computePosition(dice, true, true,false));
            }
        }
        return hashMapGrid;
    }

    public Map<String,Boolean[][]> computeAvailablePositionsNoColorNoValue(List<Dice> draftedDice) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        for (Dice dice : draftedDice) {
            if(!hashMapGrid.containsKey(dice.toString())) {
                hashMapGrid.put(dice.toString(), computePosition(dice, false, false,true));
            }
        }
        return hashMapGrid;
    }

    private Boolean[][] computePosition(Dice dice, boolean colorRestrictions, boolean valueRestrictions, boolean diceAroundRestriction) {
        Boolean[][] booleanGrid = new Boolean[4][5];

        //initialize booleanGrid

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                booleanGrid[i][j] = false;
            }
        }

        //first case: grid is empty

        if (isGridEmpty()) {

        //Check restrictions

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (hasValue(i, j)) {
                        if (sameGridValue(valueRestrictions, i, j, dice))
                            booleanGrid[i][j] = true;
                    } else if (sameGridColor(colorRestrictions, i, j, dice))
                        booleanGrid[i][j] = true;
                }
            }

            for (int i = 1; i < 3; i++) {
                for (int j = 1; j < 4; j++) {
                    booleanGrid[i][j] = false;
                }
            }

            for (int i = 0; i < 5; i++) {
                if (isBlank(0, i)) {
                    booleanGrid[0][i] = true;
                }
                if (isBlank(3, i)) {
                    booleanGrid[3][i] = true;
                }
            }

            for (int i = 0; i < 4; i++) {
                if (isBlank(i, 0))
                    booleanGrid[i][0] = true;
                if (isBlank(i, 4))
                    booleanGrid[i][4] = true;
            }
        }
        else {
            //Check dice

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (noDice(i, j)) {

                        if (!hasDicesAround(diceAroundRestriction,i, j)) {
                            booleanGrid[i][j] = false;
                            continue;
                        }
                        //Check restrictions


                        if (hasValue(i, j)) {
                            if (!sameGridValue(valueRestrictions, i, j, dice)) {
                                booleanGrid[i][j] = false;
                                continue;
                            }
                        } else if (!sameGridColor(colorRestrictions, i, j, dice) && !isBlank(i, j)) {
                            booleanGrid[i][j] = false;
                            continue;
                        }

                        //Check dices around

                        if (i < 3)
                            if (checkAround(dice, i + 1, j, j, booleanGrid[i], booleanGrid)) continue;


                        if (i > 0)
                            if (checkAround(dice, i - 1, j, j, booleanGrid[i], booleanGrid)) continue;



                        if (j < 4)
                            if (checkAround(dice, i, j, j + 1, booleanGrid[i], booleanGrid)) continue;



                        if (j > 0) {
                            checkAround(dice, i, j, j - 1, booleanGrid[i], booleanGrid);
                        }
                    }
                }
            }
        }
        return booleanGrid;
    }

    private boolean checkAround(Dice dice, int i, int j, int i2, Boolean[] booleans, Boolean[][] booleanGrid) {
        if (!noDice(i, i2)) {
            if (!sameDiceValue(i, i2, dice) && !sameDiceColor(i, i2, dice)) {
                booleans[j] = true;
            }
            else {
                booleans[j] = false;
                return true;
            }
        } else
            booleans[j] = true;
        return false;
    }

    private boolean hasDicesAround(boolean diceAroundRestriction, int i, int j) {
        if (diceAroundRestriction){
            if (j > 0) {
                if (!noDice(i, j - 1))
                    return !noDice(i,j - 1);
            }
            if (j < 4) {
                if (!noDice(i,j + 1))
                    return !noDice(i,j + 1);
            }
            if (i < 3) {
                if (!noDice(i + 1,j))
                    return !noDice(i + 1,j);
            }
            if (i > 0) {
                if (!noDice(i - 1,j))
                    return !noDice(i - 1,j);
            }
            if (j > 0 && i < 3) {
                if (!noDice(i + 1,j - 1))
                    return !noDice(i + 1,j - 1);
            }
            if (j > 0 && i > 0) {
                if (!noDice(i - 1,j - 1))
                    return !noDice(i - 1,j - 1);
            }
            if (j < 4 && i < 3) {
                if (!noDice(i + 1,j + 1))
                    return !noDice(i + 1,j + 1);
            }
            if (j < 4 && i > 0) {
                if (!noDice(i - 1,j + 1))
                    return !noDice(i - 1,j + 1);
            }
            return false;
        }
        return true;
    }

    private boolean sameDiceColor(int i, int j, Dice dice) {
        return grid.get(i).get(j).getDice().getDiceColor().equals(dice.getDiceColor());
    }

    private boolean sameDiceValue(int i, int j, Dice dice) {
        return grid.get(i).get(j).getDice().getFaceUpValue() == dice.getFaceUpValue();
    }

    private boolean sameGridColor(boolean colorRestrictions, int i, int j, Dice dice) {
        if (colorRestrictions)
            return grid.get(i).get(j).getColor().equals(dice.getDiceColor());
        return true;
    }


    private boolean sameGridValue(boolean valueRestrictions, int i, int j, Dice dice) {
        if (valueRestrictions)
            return grid.get(i).get(j).getValue().equals(dice.getFaceUpValue());
        return true;
    }

    private boolean noDice(int i, int j) {
        return !(grid.get(i).get(j).isDiceSet());
    }

    private boolean hasValue(int i, int j) {
        return grid.get(i).get(j).isValueSet();
    }

    private boolean isBlank(int i, int j) {
        return (!grid.get(i).get(j).isValueSet() && grid.get(i).get(j).getColor().equals(Color.BLANK));
    }

    private boolean isGridEmpty() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid.get(i).get(j).isDiceSet())
                    return false;
            }
        }
        return true;
    }
}

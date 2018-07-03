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

    public Box insertDiceInBox(int rowIndex, int columnIndex) {
        return grid.get(rowIndex).get(columnIndex);
    }

    private void fillGrid() {
        this.grid = new ArrayList<>(4);
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
    }

    public int getNoOfEmptyBoxes() {
        return grid.stream().
                mapToInt(row -> (int) row.stream()
                        .filter(box -> box.getDice() == null).count())
                .reduce(0, (sum, x) -> sum + x);
    }

    public Map<String,Boolean[][]> computeAvailablePositionsDraftedDice(List<Dice> draftedDice) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        for (Dice dice : draftedDice) {
            if(!hashMapGrid.containsKey(dice.toString())) {
                hashMapGrid.put(dice.toString(), computePosition(dice, true, true, true, false, 0, 0));
            }
        }
        return hashMapGrid;
    }


    public Map<String,Boolean[][]> computeAvailablePositions() {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        Dice dice;
        for (int line = 0; line < 4; line++) {
            for (int column = 0; column < 5; column++) {
                if(grid.get(line).get(column).getDice() != null) {
                    dice = grid.get(line).get(column).getDice();
                    grid.get(line).get(column).removeDice();
                    hashMapGrid.put(dice.toString() + line + column, computePosition(dice, true, true, true, false, line, column));
                    grid.get(line).get(column).insertDice(dice);
                }
            }
        }
        return hashMapGrid;
    }

    public Map<String,Boolean[][]> computeAvailablePositionsNoValue() {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        Dice dice;
        for (int line = 0; line < 4; line++) {
            for (int column = 0; column < 5; column++) {
                if(grid.get(line).get(column).getDice() != null) {
                    dice = grid.get(line).get(column).getDice();
                    grid.get(line).get(column).removeDice();
                    hashMapGrid.put(dice.toString() + line + column, computePosition(dice, true, false, true, false, line, column));
                    grid.get(line).get(column).insertDice(dice);
                }
            }
        }
        return hashMapGrid;
    }

    /**
     * Compute all available positions for Lathekin toolcard. It uses the standard algorithm and it also computes the available position for the Boxes
     * in which there is a Dice in order to do the double move.
     * @return Map with that contains the position in which you can place the dice used as key of the HashMap.
     */
    public Map<String, Boolean[][]> computeAvailablePositionsLathekin() {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        Dice dice;
        for (int line = 0; line < 4; line++) {
            for (int column = 0; column < 5; column++) {
                if(grid.get(line).get(column).getDice() != null) {
                    dice = grid.get(line).get(column).getDice();
                    grid.get(line).get(column).removeDice();
                    hashMapGrid.put(dice.toString() + line + column, computePosition(dice, true, true, true, true, line, column));
                    grid.get(line).get(column).insertDice(dice);
                }
            }
        }
        return hashMapGrid;
    }

    /**
     * Compute all available positions for Tap Wheel toolcard. If it is called on the first phase of the toolcard it uses the same algorithm of Lathekin
     * otherwise the standard algorithm for a dice in the pattern card is called.
     * @param colorDice The algorithm is executed only for the dice with the same colorDice value
     * @param firstMoveDone Switch beetwen the two algorithm strategies
     * @return Map with that contains the position in which you can place the dice used as key of the HashMap.
     */
    public Map<String, Boolean[][]> computeAvailablePositionsTapWheel(Dice colorDice, boolean firstMoveDone) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        Dice dice;
        for (int lined = 0; lined < 4; lined++) {
            for (int column = 0; column < 5; column++) {
                if(grid.get(lined).get(column).getDice() != null && grid.get(lined).get(column).getDice().getDiceColor() == colorDice.getDiceColor()) {
                    dice = grid.get(lined).get(column).getDice();
                    grid.get(lined).get(column).removeDice();

                    if (!firstMoveDone) {
                        //Re use lathekin algorithm for the double move
                        hashMapGrid.put(dice.toString() + lined + column, computePosition(dice, true, true, true, true, lined, column));
                    } else
                        hashMapGrid.put(dice.toString() + lined + column, computePosition(dice, true, true, true, false, lined, column));

                    grid.get(lined).get(column).insertDice(dice);

                    System.out.println(dice.toString() +lined +column);
                    Boolean[][] x = hashMapGrid.get(dice.toString() +lined +column);
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 5; l++) {
                            System.out.print(x[k][l] + "\t");
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
            }
        }
        return hashMapGrid;
    }

    public Map<String,Boolean[][]> computeAvailablePositionsNoColor() {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        Dice dice;
        for (int line = 0; line < 4; line++) {
            for (int column = 0; column < 5; column++) {
                if(grid.get(line).get(column).getDice() != null) {
                    dice = grid.get(line).get(column).getDice();
                    grid.get(line).get(column).removeDice();
                    hashMapGrid.put(dice.toString() + line + column, computePosition(dice, false, true, true, false, line, column));
                    grid.get(line).get(column).insertDice(dice);
                }
            }
        }
        return hashMapGrid;
    }

    public Map<String,Boolean[][]> computeAvailablePositionsNoDiceAround(List<Dice> draftedDice) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        for (Dice dice : draftedDice) {
            if(!hashMapGrid.containsKey(dice.toString())) {
                hashMapGrid.put(dice.toString(), computePosition(dice, true, true, false, false, 0, 0));
            }
        }
        return hashMapGrid;
    }


    private Boolean[][] computePosition(Dice dice, boolean colorRestrictions, boolean valueRestrictions, boolean diceAroundRestriction, boolean lathekin, int diceLine, int diceColumn) {
        Boolean[][] booleanGrid = new Boolean[4][5];

        //initialize booleanGrid

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                booleanGrid[i][j] = false;
            }
        }

        //first case: grid is empty

        if (getNoOfDice() == 0) {

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
        } else {
            //Check dice

            for (int line = 0; line < 4; line++) {
                for (int column = 0; column < 5; column++) {
                    if (noDice(line, column) || lathekin) {

                        /*
                        Only for double move (Lathekin && TapWheel):
                        First of all check if the dice in the box can be switched with the one that it's been analyzed.
                        Analyze if the dice in position [line, column] can be placed in the position of the current dice [diceLine, diceColumn]
                         */

                        if (!singleBoxCheck(dice, colorRestrictions, valueRestrictions, diceAroundRestriction, booleanGrid, line, column)) {
                            continue;
                        }

                        if (lathekin && (line != diceLine && column != diceColumn) && grid.get(line).get(column).getDice() != null) {
                            boxToSwitchCheck(grid.get(line).get(column).getDice(), colorRestrictions, valueRestrictions, diceAroundRestriction, booleanGrid, line, column, diceLine, diceColumn);
                        }

                    }
                }
            }
        }
        return booleanGrid;
    }

    private boolean boxToSwitchCheck(Dice dice, boolean colorRestrictions, boolean valueRestrictions, boolean diceAroundRestriction, Boolean[][] booleanGrid, int line, int column, int diceLine, int diceColumn) {
        //Check if there are dices around the box

        if (checkIfDiceAround(diceAroundRestriction, booleanGrid, line, column, diceLine, diceColumn)) return false;


        //Check restrictions

        if (hasValue(diceLine, diceColumn)) {
            if (!sameGridValue(valueRestrictions, diceLine, diceColumn, dice)) {
                booleanGrid[line][column] = false;
                return false;
            } else if (!diceAroundRestriction) {
                booleanGrid[line][column] = true;
                return true;
            }
        } else {
            if (!sameGridColor(colorRestrictions, diceLine, diceColumn, dice) && !isBlank(diceLine, diceColumn)) {
                booleanGrid[line][column] = false;
                return false;
            } else if (!diceAroundRestriction) {
                booleanGrid[line][column] = true;
                return true;
            }
        }

        //Check dices around

        if (diceLine < 3)
            if (checkAroundSwitchBox(dice, diceLine + 1, diceColumn, line, column, booleanGrid))
                return colorRestrictions;

        if (diceLine > 0)
            if (checkAroundSwitchBox(dice, diceLine - 1, diceColumn, line, column, booleanGrid))
                return colorRestrictions;

        if (diceColumn < 4)
            if (checkAroundSwitchBox(dice, diceLine, diceColumn + 1, line, column, booleanGrid))
                return colorRestrictions;

        if (diceColumn > 0) {
            checkAroundSwitchBox(dice, diceLine, diceColumn - 1, line, column, booleanGrid);
        }
        return colorRestrictions;
    }

    private boolean checkIfDiceAround(boolean diceAroundRestriction, Boolean[][] booleanGrid, int line, int column, int diceLine, int diceColumn) {
        if (!hasDicesAround(diceLine, diceColumn)) {
            if (!diceAroundRestriction) {
                booleanGrid[line][column] = true;
            } else {
                booleanGrid[line][column] = false;
                return true;
            }
        } else if (!diceAroundRestriction) {
            booleanGrid[line][column] = false;
            return true;
        }
        return false;
    }

    private boolean singleBoxCheck(Dice dice, boolean colorRestrictions, boolean valueRestrictions, boolean diceAroundRestriction, Boolean[][] booleanGrid, int line, int column) {
        //Check if there are dices around the box

        if (checkIfDiceAround(diceAroundRestriction, booleanGrid, line, column, line, column)) return false;


        //Check restrictions

        if (hasValue(line, column)) {
            if (!sameGridValue(valueRestrictions, line, column, dice)) {
                booleanGrid[line][column] = false;
                return false;
            } else if (!diceAroundRestriction) {
                booleanGrid[line][column] = true;
                return true;
            }
        } else {
            if (!sameGridColor(colorRestrictions, line, column, dice) && !isBlank(line, column)) {
                booleanGrid[line][column] = false;
                return false;
            } else if (!diceAroundRestriction) {
                booleanGrid[line][column] = true;
                return true;
            }
        }

        //Check dices around

        if (line < 3)
            if (checkAround(dice, line + 1, column, column, booleanGrid[line])) return false;


        if (line > 0)
            if (checkAround(dice, line - 1, column, column, booleanGrid[line])) return false;


        if (column < 4)
            if (checkAround(dice, line, column, column + 1, booleanGrid[line])) return false;


        if (column > 0) {
            if (checkAround(dice, line, column, column - 1, booleanGrid[line])) return false;
        }

        return true;
    }

    private boolean checkAround(Dice dice, int line, int column, int neighbour, Boolean[] booleans) {
        if (!noDice(line, neighbour)) {
            if (!sameDiceValue(line, neighbour, dice) && !sameDiceColor(line, neighbour, dice)) {
                booleans[column] = true;
            }
            else {
                booleans[column] = false;
                return true;
            }
        } else
            booleans[column] = true;
        return false;
    }


    private boolean checkAroundSwitchBox(Dice dice, int diceLine, int diceColumn, int line, int column, Boolean[][] booleans) {
        if (!noDice(diceLine, diceColumn)) {
            if (!sameDiceValue(diceLine, diceColumn, dice) && !sameDiceColor(diceLine, diceColumn, dice)) {
                booleans[line][column] = true;
            }
            else {
                booleans[line][column] = false;
                return true;
            }
        } else
            booleans[line][column] = true;
        return false;
    }

    private boolean hasDicesAround(int i, int j) {
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

    public boolean isGridEmpty() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid.get(i).get(j).isDiceSet())
                    return false;
            }
        }
        return true;
    }


    public int getNoOfDice() {
        return grid.stream().mapToInt(boxes -> (int) boxes.stream()
                .filter(box -> box.getDice() != null)
                .count())
                .reduce(0,(sum, x) -> sum + x);
    }
}

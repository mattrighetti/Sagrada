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

    /**
     * Set the pattern card name, instantiate the grid and set the difficulty
     */
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

    /**
     * Method that returns the Patterncard difficulty.
     *
     * @return Difficulty of the patterncard
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Method that sets the grid of the patterncard.
     *
     * @param grid The grid to set.
     */
    public void setGrid(List<List<Box>> grid) {
        this.grid = grid;
    }

    /**
     * Method that returns the grid of the patterncard.
     */
    public List<List<Box>> getGrid() {
        return grid;
    }

    /**
     * Method that insert a Dice in the Box with position [rowIndex, columnIndex]
     *
     * @param rowIndex Index of the row in the grid.
     * @param columnIndex Index of the column in the grid.
     * @return
     */
    public Box insertDiceInBox(int rowIndex, int columnIndex) {
        return grid.get(rowIndex).get(columnIndex);
    }

    /**
     * Method that instantiates the grid.
     */
    private void fillGrid() {
        this.grid = new ArrayList<>(4);
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
    }

    /**
     * Method that returns the number of Boxes in which there is no dice setted.
     *
     * @return Number of Empty Boxes
     */
    public int getNoOfEmptyBoxes() {
        return grid.stream().
                mapToInt(row -> (int) row.stream()
                        .filter(box -> box.getDice() == null).count())
                .reduce(0, (sum, x) -> sum + x);
    }

    /**
     * Method that computes the position of the grid in which you can place a dice picked from the
     * drafted dice.
     *
     * @param draftedDice List of dice on which calculate the available positions
     * @return <code>HashMap<String, Boolean[][]></code>. The key used is the Dice.toString() and the
     *          value is a 2-D Array containing the available positions
     */
    public Map<String,Boolean[][]> computeAvailablePositionsDraftedDice(List<Dice> draftedDice) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        for (Dice dice : draftedDice) {
            if(!hashMapGrid.containsKey(dice.toString())) {
                hashMapGrid.put(dice.toString(), computePosition(dice, true, true, true, false, 0, 0));
            }
        }
        return hashMapGrid;
    }

    /**
     * Method that computes the position of the grid in which you can place a dice picked from the
     * patterncard.
     *
     * @return <code>HashMap<String, Boolean[][]></code>. The key used is the Dice.toString() and the
     *          value is a 2-D Array containing the available positions.
     */
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

    /**
     * Method that computes the position of the grid in which you can place a dice picked from the
     * patterncard avoiding the value restrictions (referring to the value setted in the Box if it is present).
     *
     * @return <code>HashMap<String, Boolean[][]></code>. The key used is the Dice.toString() and the
     *          value is a 2-D Array containing the available positions.
     */
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
     *
     * @return <code>HashMap<String, Boolean[][]></code>. The key used is the Dice.toString()+line+column
     *         (line and column are the index of the analyzed dice in the grid and the value is a 2-D Array
     *          containing the available positions.
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
     * otherwise the standard algorithm for a dice in the pattern card is called (<code>computeAvailablePositions()</code>
     *
     * @param colorDice The algorithm is executed only for the dice with the same colorDice value
     * @param firstMoveDone Switch beetwen the two algorithm strategies
     * @return <code>HashMap<String, Boolean[][]></code>. The key used is the Dice.toString()+line+column
     *         (line and column are the index of the analyzed dice in the grid and the value is a 2-D Array
     *         containing the available positions.
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

    /**
     * Method that computes the position of the grid in which you can place a dice picked from the
     * patterncard avoiding the color restrictions (referring to the color setted in the Box if it is present).
     *
     * @return <code>HashMap<String, Boolean[][]></code>. The key used is the Dice.toString()+line+column
     *          (line and column are the index of the analyzed dice in the grid and the value is a 2-D Array
     *           containing the available positions.
     */
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

    /**
     * Method that computes the position of the grid in which you can place a dice picked from the
     * patterncard avoiding the dice around restrictions (used in CorkBackedStraightEdge).
     *
     * @return <code>HashMap<String, Boolean[][]></code>. The key used is the Dice.toString() and the
     *          value is a 2-D Array containing the available positions.
     */
    public Map<String,Boolean[][]> computeAvailablePositionsNoDiceAround(List<Dice> draftedDice) {
        HashMap<String,Boolean[][]> hashMapGrid = new HashMap<>();
        for (Dice dice : draftedDice) {
            if(!hashMapGrid.containsKey(dice.toString())) {
                hashMapGrid.put(dice.toString(), computePosition(dice, true, true, false, false, 0, 0));
            }
        }
        return hashMapGrid;
    }

    /**
     * Main algorithm for computing available positions. First of all checks if the grid is empty
     * and if it's true only the border positions are setted to true. Otherwise it iterates over the Boxes and:
     * 1 - Check if there are dices around
     * 2 - Check if the dice color and value are the same of the one setted in the Box
     * 3 - Check the constraint created by the dice placed around the Box analyzed.
     * 4 - Checks also the Boxes in which a dice is setted(only if lathekin field is setted to true).
     * 5 - Checks, in case of double move(Lathekin & TapWheel), if both dices can be inserted in the inverted positions.
     *
     * @param dice Dice to compute available positions for
     * @param colorRestrictions Enables color restrictions.
     * @param valueRestrictions Enables value restrictions.
     * @param diceAroundRestriction Enables that a dice, in order to be placed, must be adiacent to another one.
     * @param lathekin Enables the control for also the boxes with a dice already setted.
     * @param diceLine Line index from <code>dice</code> is extracted (used only for Lathekin & Tapwheel).
     * @param diceColumn Column index from <code>dice</code> is extracted (used only for Lathekin & Tapwheel).
     * @return 2-D array of boolean. With a true value the dice can be placed otherwise no.
     */
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

    /**
     * Check if the dice can be "swapped" with a double move. It always check dicearound, color and value restrictions
     * and also the other dice constraints.
     *
     * @param dice Dice to compute available positions for
     * @param colorRestrictions Enables color restrictions.
     * @param valueRestrictions Enables value restrictions.
     * @param diceAroundRestriction Enables that a dice, in order to be placed, must be adiacent to another one.
     * @param booleanGrid The result grid that has to be updated meanwhile the Boxes have been analyzed.
     * @param diceLine Line index from <code>dice</code> is extracted (used only for Lathekin & Tapwheel).
     * @param diceColumn Column index from <code>dice</code> is extracted (used only for Lathekin & Tapwheel).
     * @return Returns true if the dice can be swapped.
     */
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

    /**
     * Check if there are dices around the given box(picked by using <code>line</code> and <code>column</code>.
     *
     * @param diceAroundRestriction Enables diceAround restriction.
     * @param booleanGrid The result grid that has to be updated meanwhile the Boxes have been analyzed.
     * @param line Line index of the current Box.
     * @param column Column index of the current Box.
     * @param diceLine Line index from <code>dice</code> is extracted (used only for Lathekin & Tapwheel).
     * @param diceColumn Column index from <code>dice</code> is extracted (used only for Lathekin & Tapwheel).
     * @return Returns true if there are dices around, otherwise false.
     */
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

    /**
     * Check if a dice can be placed in the given position.
     *
     * @param dice Dice to compute available positions for.
     * @param colorRestrictions Enables color restrictions.
     * @param valueRestrictions Enables value restrictions.
     * @param diceAroundRestriction Enables that a dice, in order to be placed, must be adiacent to another one.
     * @param booleanGrid The result grid that has to be updated meanwhile the Boxes have been analyzed.
     * @return 2-D array of boolean. With a true value the dice can be placed otherwise no.

     */
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

    /**
     * Check the constraint of a specific neighbour dice(only if it's present) by given the
     * line and column index of the neighbour(only orthogonal positions will be checked):
     * the dice can't be placed if it has the same color or value of the neighbour.
     *
     * @param dice Dice to compute available positions for.
     * @param line Line index of the neighbour.
     * @param column Column index of the dice
     * @param neighbour Column index of the neighbour.
     * @param booleans 1-D array, it is a section of the resultGrid(the one corresponding at the dice line index).
     * @return Returns if the constraints of the neighbour are not respected
     */
    private boolean checkAround(Dice dice, int line, int column, int neighbour, Boolean[] booleans) {
        if (!noDice(line, neighbour)) {
            if (notSameDiceValue(line, neighbour, dice) && notSameDiceColor(line, neighbour, dice)) {
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


    /**
     * Check the constraint of a specific neighbour dice(only if it's present) by given the
     * line and column index of the neighbour(only orthogonal positions will be checked):
     * the dice can't be placed if it has the same color or value of the neighbour.
     * During the calculation it uses also the initial diceLine and diceColumn indexes to calculate
     * if the dice can be swapped.
     *
     * @param dice Dice to compute available positions for.
     * @param line Line index of the neighbour.
     * @param column Column index of the dice
     * @param booleans 2-D array, it is the resultGrid.
     * @return Returns if the constraints of the neighbour are not respected
     */
    private boolean checkAroundSwitchBox(Dice dice, int diceLine, int diceColumn, int line, int column, Boolean[][] booleans) {
        if (!noDice(diceLine, diceColumn)) {
            if (notSameDiceValue(diceLine, diceColumn, dice) && notSameDiceColor(diceLine, diceColumn, dice)) {
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

    /**
     * Check if there are dices around the given Box.
     *
     * @param line Line index.
     * @param column Column index.
     * @return Returns true if there is a dice in diagonal or orthogonal position.
     */
    private boolean hasDicesAround(int line, int column) {
            if (column > 0) {
                if (!noDice(line, column - 1))
                    return !noDice(line,column - 1);
            }
            if (column < 4) {
                if (!noDice(line,column + 1))
                    return !noDice(line,column + 1);
            }
            if (line < 3) {
                if (!noDice(line + 1,column))
                    return !noDice(line + 1,column);
            }
            if (line > 0) {
                if (!noDice(line - 1,column))
                    return !noDice(line - 1,column);
            }
            if (column > 0 && line < 3) {
                if (!noDice(line + 1,column - 1))
                    return !noDice(line + 1,column - 1);
            }
            if (column > 0 && line > 0) {
                if (!noDice(line - 1,column - 1))
                    return !noDice(line - 1,column - 1);
            }
            if (column < 4 && line < 3) {
                if (!noDice(line + 1,column + 1))
                    return !noDice(line + 1,column + 1);
            }
            if (column < 4 && line > 0) {
                if (!noDice(line - 1,column + 1))
                    return !noDice(line - 1,column + 1);
            }
            return false;

    }

    /**
     * Check if the dice has the same color of the selected dice.
     *
     * @param line Line index.
     * @param column Column index.
     * @param dice Dice to be compared.
     * @return Returns true if they have not the same color.
     */
    private boolean notSameDiceColor(int line, int column, Dice dice) {
        return !grid.get(line).get(column).getDice().getDiceColor().equals(dice.getDiceColor());
    }


    /**
     * Check if the dice has the same value of the selected dice.
     *
     * @param line Line index.
     * @param column Column index.
     * @param dice Dice to be compared.
     * @return Returns true if they have not the same value.
     */
    private boolean notSameDiceValue(int line, int column, Dice dice) {
        return grid.get(line).get(column).getDice().getFaceUpValue() != dice.getFaceUpValue();
    }

    /**
     * Check if the dice has the same color of the selected Box.
     *
     * @param line Line index.
     * @param column Column index.
     * @param dice Dice to be compared.
     * @param colorRestrictions Computes only if the color restrictions is enabled.
     * @return Returns true if they have the same color.
     */
    private boolean sameGridColor(boolean colorRestrictions, int line, int column, Dice dice) {
        if (colorRestrictions)
            return grid.get(line).get(column).getColor().equals(dice.getDiceColor());
        return true;
    }

    /**
     * Check if the dice has the same value of the selected Box.
     *
     * @param line Line index.
     * @param column Column index.
     * @param dice Dice to be compared.
     * @param valueRestrictions Computes only if the value restrictions is enabled.
     * @return Returns true if they have the same value.
     */
    private boolean sameGridValue(boolean valueRestrictions, int line, int column, Dice dice) {
        if (valueRestrictions)
            return grid.get(line).get(column).getValue().equals(dice.getFaceUpValue());
        return true;
    }

    /**
     * Check if there are no dices around.
     *
     * @param line Line index of the Box to check.
     * @param column Column index of the Box to check.
     * @return
     */
    private boolean noDice(int line, int column) {
        return !(grid.get(line).get(column).isDiceSet());
    }

    /**
     * Check if the Box has a value set.
     * @param line Line index
     * @param column Column index
     * @return True if value is set.
     */
    private boolean hasValue(int line, int column) {
        return grid.get(line).get(column).isValueSet();
    }

    /**
     * Check if the Box is Blank
     * @param line Line index
     * @param column Column index
     * @return True if color is blank
     */
    private boolean isBlank(int line, int column) {
        return (!grid.get(line).get(column).isValueSet() && grid.get(line).get(column).getColor().equals(Color.BLANK));
    }

    /**
     * Check if the grid is empty.
     * @return true if is empty.
     */
    public boolean isGridEmpty() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid.get(i).get(j).isDiceSet())
                    return false;
            }
        }
        return true;
    }

    /**
     * @return Returns the number of dice in the grid.
     */
    public int getNoOfDice() {
        return grid.stream().mapToInt(boxes -> (int) boxes.stream()
                .filter(box -> box.getDice() != null)
                .count())
                .reduce(0,(sum, x) -> sum + x);
    }
}

package ingsw.model.cards.patterncard;

import ingsw.model.Color;
import ingsw.model.Dice;
import ingsw.model.cards.Card;

import java.util.ArrayList;
import java.util.List;

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
        this.grid = new ArrayList<List<Box>>(4);
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
        this.grid.add(new ArrayList<>(5));
    }

    public List<Boolean[][]> computeAvailablePositions(List<Dice> draftedDice) {
        List<Boolean[][]> booleanGridsList = new ArrayList<>();
        for (Dice dice : draftedDice) {
            booleanGridsList.add(computePosition(dice));
        }
        return booleanGridsList;
    }

    private Boolean[][] computePosition(Dice dice) {
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
                        if (sameValue(i, j, dice))
                            booleanGrid[i][j] = true;
                    } else if (grid.get(i).get(j).getColor().equals(dice.getDiceColor()))
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
                    if (isDiceSet(i, j)) {
                        if (diceColorOrValue(i, j, dice)) {

                            //check orthogonal

                            if (i < 3) {
                                if (noDice(i + 1, j))
                                    if (hasValue(i + 1, j)) {
                                        if (sameValue(i + 1, j, dice))
                                            booleanGrid[i + 1][j] = true;
                                    } else if (sameOrBlankColor(i + 1, j, dice))
                                        booleanGrid[i + 1][j] = true;
                            }

                            if (i > 0) {
                                if (noDice(i - 1, j))
                                    if (hasValue(i - 1, j)) {
                                        if (sameValue(i - 1, j, dice))
                                            booleanGrid[i - 1][j] = true;
                                    } else if (sameOrBlankColor(i - 1, j, dice))
                                        booleanGrid[i - 1][j] = true;
                            }

                            if (j > 0) {
                                if (noDice(i, j - 1))
                                    if (hasValue(i, j - 1)) {
                                        if (sameValue(i, j - 1, dice))
                                            booleanGrid[i][j - 1] = true;
                                    } else if (sameOrBlankColor(i, j - 1, dice))
                                        booleanGrid[i][j - 1] = true;
                            }

                            if (j < 4) {
                                if (noDice(i, j + 1))
                                    if (hasValue(i, j + 1)) {
                                        if (sameValue(i, j + 1, dice))
                                            booleanGrid[i][j + 1] = true;
                                    } else if (sameOrBlankColor(i, j + 1, dice))
                                        booleanGrid[i][j + 1] = true;
                            }

                        }

                        //check diagonals

                        if (i < 3 && j < 4) {
                            if (noDice(i + 1, j + 1))
                                if (hasValue(i + 1, j + 1)) {
                                    if (sameValue(i + 1, j + 1, dice))
                                        booleanGrid[i + 1][j + 1] = true;
                                } else if (sameOrBlankColor(i + 1, j + 1, dice))
                                    booleanGrid[i + 1][j + 1] = true;
                        }

                        if (i > 0 && j > 0) {
                            if (noDice(i - 1, j - 1))
                                if (hasValue(i - 1, j - 1)) {
                                    if (sameValue(i - 1, j - 1, dice))
                                        booleanGrid[i - 1][j - 1] = true;
                                } else if (sameOrBlankColor(i - 1, j - 1, dice))
                                    booleanGrid[i - 1][j - 1] = true;
                        }

                        if (i > 0 && j < 4) {
                            if (noDice(i - 1, j + 1))
                                if (hasValue(i - 1, j + 1)) {
                                    if (sameValue(i - 1, j + 1, dice))
                                        booleanGrid[i - 1][j + 1] = true;
                                } else if (sameOrBlankColor(i - 1, j + 1, dice))
                                    booleanGrid[i - 1][j + 1] = true;
                        }

                        if (i < 3 && j > 0) {
                            if (noDice(i + 1, j - 1))
                                if (hasValue(i + 1, j - 1)) {
                                    if (sameValue(i + 1, j - 1, dice))
                                        booleanGrid[i + 1][j - 1] = true;
                                } else if (sameOrBlankColor(i + 1, j - 1, dice))
                                    booleanGrid[i + 1][j - 1] = true;
                        }
                    }
                }
            }
        }
        return booleanGrid;
    }

    private boolean sameOrBlankColor(int i, int j, Dice dice) {
        return grid.get(i).get(j).getColor().equals(dice.getDiceColor()) || grid.get(i).get(j).getColor().equals(Color.BLANK);
    }

    private boolean sameValue(int i, int j, Dice dice) {
        return grid.get(i).get(j).getValue().equals(dice.getFaceUpValue());
    }

    private boolean noDice(int i, int j) {
        return !(grid.get(i).get(j).isDiceSet());
    }

    private boolean hasValue(int i, int j) {
        return grid.get(i).get(j).isValueSet();
    }

    private boolean isDiceSet(int i, int j){
        return grid.get(i).get(j).isDiceSet();
    }

    private boolean diceColorOrValue(int i, int j, Dice dice) {
        return !(grid.get(i).get(j).getDice().getDiceColor().equals(dice.getDiceColor()) ||
                        grid.get(i).get(j).getDice().getFaceUpValue() == dice.getFaceUpValue());
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

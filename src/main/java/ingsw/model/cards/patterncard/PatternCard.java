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
                        if (sameGridValue(i, j, dice))
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
                    if (noDice(i, j)) {

                        if (!hasDicesAround(i, j)) {
                            booleanGrid[i][j] = false;
                            continue;
                        }
                        //Check restrictions


                        if (hasValue(i, j)) {
                            if (!sameGridValue(i, j, dice)) {
                                booleanGrid[i][j] = false;
                                continue;
                            }
                        } else if (!sameGridColor(i, j, dice) && !isBlank(i, j)) {
                            booleanGrid[i][j] = false;
                            continue;
                        }

                        //Check dices around

                        if (i < 3) {
                            if (!noDice(i + 1, j)) {
                                if (!sameDiceValue(i + 1, j, dice) && !sameDiceColor(i + 1, j, dice)) {
                                    booleanGrid[i][j] = true;
                                }
                                else {
                                    booleanGrid[i][j] = false;
                                    continue;
                                }
                            } else
                                booleanGrid[i][j] = true;
                        }

                        if (i > 0) {
                            if (!noDice(i - 1, j)) {
                                if (!sameDiceValue(i - 1, j, dice) && !sameDiceColor(i - 1, j, dice)) {
                                    booleanGrid[i][j] = true;
                                }
                                else {
                                    booleanGrid[i][j] = false;
                                    continue;
                                }
                            } else
                                booleanGrid[i][j] = true;
                        }


                        if (j < 4) {
                            if (!noDice(i, j + 1)) {
                                if (!sameDiceValue(i, j + 1, dice) && !sameDiceColor(i, j + 1, dice)) {
                                    booleanGrid[i][j] = true;
                                }
                                else {
                                    booleanGrid[i][j] = false;
                                    continue;
                                }
                            } else
                                booleanGrid[i][j] = true;
                        }


                        if (j > 0) {
                            if (!noDice(i, j - 1)) {
                                if (!sameDiceValue(i, j - 1, dice) && !sameDiceColor(i, j - 1, dice)) {
                                    booleanGrid[i][j] = true;
                                } else {
                                    booleanGrid[i][j] = false;
                                    continue;
                                }
                            } else
                                booleanGrid[i][j] = true;
                        }
                    }
                }
            }
        }
        return booleanGrid;
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

    private boolean sameGridColor(int i, int j, Dice dice) {
        return grid.get(i).get(j).getColor().equals(dice.getDiceColor());
    }


    private boolean sameGridValue(int i, int j, Dice dice) {
        return grid.get(i).get(j).getValue().equals(dice.getFaceUpValue());
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

package ingsw.model.cards.patterncard;

import ingsw.model.cards.Card;

public abstract class PatternCard extends Card {
    private int difficulty;
    private Box grid[][];

    /**
     * TODO:This method will probably be reimplementated because the single card will probably be saved in a file
     * @param difficulty
     */
    protected void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Box[][] getGrid() {
        return grid;
    }

    public void setGrid(Box[][] grid) {
        this.grid = grid;
    }
}

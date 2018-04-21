package ingsw.model.cards.patterncard;

import ingsw.model.cards.Card;

public abstract class PatternCard extends Card {
    private int difficulty;
    private Box[][] grid;

    public PatternCard(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
        this.grid = new Box[4][5];
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

    public Box[][] getGrid() {
        return grid;
    }

    protected void setGrid(Box[][] grid) {
        this.grid = grid;
    }
}

package ingsw.model.cards.patterncard;

import ingsw.model.cards.Card;

public abstract class PatternCard extends Card {
    private int difficulty;
    private Box grid[][];

    public PatternCard(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "PatternCard{" +
                "name='" + getName() + '\'' +
                '}';
    }

    public Box[][] getGrid() {
        return grid;
    }

    public void setGrid(Box[][] grid) {
        this.grid = grid;
    }
    //TODO will this be used?
}

package ingsw.model.cards.patterncard;

import ingsw.model.Color;
import ingsw.model.cards.Card;

public abstract class PatternCard extends Card {
    private int difficulty;
    private Box[][] grid;

    public PatternCard(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "PatternCard{" +
                "'" + getName() + "'" +
                '}';
    }

    public abstract void initPatternCard();

    public Box[][] getGrid() {
        return grid;
    }

    public void setGrid(Box[][] grid) {
        this.grid = grid;
    }

    public void setBoxValue(int line, int column, int value) {
        (grid[line][column]).setValue(value);
    }

    public void setBoxColor(int line, int column, Color color) {
        (grid[line][column]).setColor(color);
    }
}

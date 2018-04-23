package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class ShadeVariety extends ShadeCard {

    public ShadeVariety() {
        super("ShadeVariety", 5);
    }

    @Override
    public int check(List<List<Box>> grid) {
        int minValue = count(grid, 1);
        for (int i = 2; i < 7; i++) {
            minValue = Math.min(minValue, count(grid, i));
        }
        return minValue;
    }

    @Override
    public int getScore(List<List<Box>> grid) {
        return getPoints() * check(grid);
    }

}

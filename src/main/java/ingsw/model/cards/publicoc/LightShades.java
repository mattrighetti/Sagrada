package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class LightShades extends ShadeCard {
    final int firstShade, secondShade;

    public LightShades() {
        super("LightShades", 2);
        this.firstShade = 1;
        this.secondShade = 2;
    }

    /**
     * Count how many dices with firstShade (value) are in the grid
     * @return the number of occurences
     */
    public int getFirstShade() {
        return firstShade;
    }

    /**
     * Count how many dices with SecondShade (value) are in the grid
     * @return the number of occurences
     */
    public int getSecondShade() {
        return secondShade;
    }

    @Override
    public int check(List<List<Box>> grid) {
        return Math.min(count(grid, getFirstShade()), count(grid, getSecondShade()));
    }

    /**
     * @param grid
     * @return the points gained with this card
     */
    @Override
    public int getScore(List<List<Box>> grid) {
        return getPoints() * check(grid);
    }

}

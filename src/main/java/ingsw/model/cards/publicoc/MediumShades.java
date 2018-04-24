package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class MediumShades extends ShadeCard {
    final int firstShade, secondShade;

    public MediumShades() {
        super("MediumShades", 2);
        this.firstShade = 3;
        this.secondShade = 4;
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

    /**
     * @param grid
     * @return the minimum number the occorences of one of the two shades checked
     */
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

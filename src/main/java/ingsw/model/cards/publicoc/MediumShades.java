package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

/**
 * Public Objective Card that counts how many sets of 3 and 4 are in the grid
 */
public class MediumShades extends ShadeCard {
    final int firstShade, secondShade;

    /**
     * Create a Medium Shades instance. Calls <code>super()</code> with parameters the card name "MediumShades" and
     * 2 that represents its points. Set firstShade = 3 and secondShade = 4
     */
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
     * Count the number of sets of 5 and 6 in the grid
     * @param grid Grid to check
     * @return the minimum number the occurences of one of the two shades checked
     */
    @Override
    public int check(List<List<Box>> grid) {
        return Math.min(count(grid, getFirstShade()), count(grid, getSecondShade()));
    }
}

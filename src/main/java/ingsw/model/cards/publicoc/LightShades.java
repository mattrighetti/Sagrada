package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

/**
 * Public Objective Card that counts how many sets of 1 and 2 are in the grid
 */
public class LightShades extends ShadeCard {
    final int firstShade, secondShade;

    /**
     * Create a Light Shades instance. Calls <code>super()</code> with parameters the card name "LightShades" and
     * 2 that represents its points. Set firstShade = 1 and secondShade = 2
     */
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

    /**
     * Count the number of sets of 5 and 6 in the grid
     * @param grid Grid to check
     * @return The number of sets of 5 and 6 in the grid
     */
    @Override
    public int check(List<List<Box>> grid) {
        return Math.min(count(grid, getFirstShade()), count(grid, getSecondShade()));
    }
}

package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

/**
 * Public Objective Card that counts how many sets of 5 and 6 are in the grid
 */
public class DeepShades extends ShadeCard {
    final int firstShade, secondShade;

    /**
     * Create a Deep Shades instance. Calls <code>super()</code> with parameters the card name "DeepShades" and
     * 2 that represents its points. Set First Shade = 5 and Second Shade = 6
     */
    public DeepShades() {
        super("DeepShades", 2);
        this.firstShade = 5;
        this.secondShade = 6;
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

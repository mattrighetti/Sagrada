package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

/**
 * Public Objective Card that counts the complete sets of shades
 */
public class ShadeVariety extends ShadeCard {

    /**
     * Create a Shade Variety instance. Calls <code>super()</code> with parameters the card name "ShadeVariety" and
     * 5 that represents its points.
     */
    public ShadeVariety() {
        super("ShadeVariety", 5);
    }

    /**
     * Count how many dices sets with all the value are in the grid
     * @param grid Grid to check
     * @return the number of sets found
     */
    @Override
    public int check(List<List<Box>> grid) {
        int minValue = count(grid, 1);
        for (int i = 2; i < 7; i++) {
            minValue = Math.min(minValue, count(grid, i));
        }
        return minValue;
    }
}

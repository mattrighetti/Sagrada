package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.List;

/**
 * Public Objective Card that checks how many dice set with all the colors are in the grid
 */
public class ColorVariety extends PublicObjectiveCard {

    /**
     * Create a Color Variety instance. Calls <code>super()</code> with parameters the card name "ColorVariety" and
     * 4 that represents its points
     */
    public ColorVariety() {
        super("ColorVariety", 4);
    }

    /**
     * Count how many dices sets with all the colors are in the grid
     * @param grid Grid to check
     * @return The number of the complete sets of all colors
     */
    @Override
    public int check(List<List<Box>> grid) {
        int minValue = 5;
        for (Color color : Color.values()) {
            if (!color.equals(Color.BLANK))
                minValue = Math.min(minValue, countColor(grid, color));
        }
        return minValue;
    }

    /**
     * Count how many dices of the color passed to the method are in the grid
     * @param grid Grid to check
     * @param color Number of dice for each color
     * @return The number of dices found
     */
    private int countColor(List<List<Box>> grid, Color color) {
        return grid.stream()
                .mapToInt(boxes -> (int) boxes.stream()
                        .filter(box -> box.getDice() != null)
                        .map(y -> y.getDice().getDiceColor())
                        .filter(y -> y.equals(color)).count())
                .reduce(0, (sum, x) -> sum + x);
    }
}

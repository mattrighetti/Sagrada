package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class ColorVariety extends PublicObjectiveCard {

    public ColorVariety() {
        super("ColorVariety", 4);
    }

    /**
     * Count how many dices sets with all the colors are in the grid
     * @param grid
     * @return
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
     * @param grid
     * @param color
     * @return the number of dices found
     */
    private int countColor(List<List<Box>> grid, Color color) {
        return grid.stream()
                .mapToInt(boxes -> (int) boxes.stream()
                        .filter(box -> box.getDice() != null)
                        .map(y -> y.getDice().getDiceColor())
                        .filter(y -> y.equals(color)).count())
                .reduce(0, (sum, x) -> sum + x);
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

package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class for the Publcic Objective Cards with "Shade" word in the name
 */
public abstract class ShadeCard extends PublicObjectiveCard {

    /**
     * Calls <code>super()</code> with String name as parameter and set int points
     * @param name String Card name
     * @param points int Card points
     */
    public ShadeCard(String name, int points) {
        super(name, points);
    }

    /**
     * Checks how many times the goal of the card is achieved
     * @param grid Grid to check
     * @return Number of times the goal is achieved
     */
    public abstract int check(List<List<Box>> grid);

    /**
     * Count how many occurrences of a value are in the grid
     * @param grid Grid to check
     * @param valueToCount Value to search
     * @return How many times the parameter <code>valueToCount</code> is in the grid
     */
    public int count(List<List<Box>> grid, int valueToCount) {
        return grid.stream()
                .mapToInt(boxes -> (int) boxes.stream()
                        .filter(box -> box.getDice() != null)
                        .mapToInt(box -> box.getDice().getFaceUpValue())
                        .filter(i -> i == valueToCount)
                        .count())
                .reduce(0, (sum, x) -> sum + x);
    }
}

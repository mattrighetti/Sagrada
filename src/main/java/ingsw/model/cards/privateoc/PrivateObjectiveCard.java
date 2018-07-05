package ingsw.model.cards.privateoc;

import ingsw.model.Color;
import ingsw.model.cards.Card;
import ingsw.model.cards.patterncard.Box;

import java.util.List;

/**
 * A Private objective card has a color in order to assign extra point to the player
 */
public class PrivateObjectiveCard extends Card {

    private Color color;

    /**
     * Creates a new PrivateObjectiveCard tool card
     */
    public PrivateObjectiveCard(Color color) {
        super(color.toString());
        this.color = color;
    }

    /**
     * Get the color of the private objective card
     * @return Private objective card color
     */
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "PrivateObjCard{'" + getName() +
                "'}";
    }

    /**
     * Count how many dice of the same color related to this PrivateCard are in the grid
     *
     * @param grid Grid to check
     * @return the number of occurrences
     */
    public int check(List<List<Box>> grid) {
        return grid.stream().
                mapToInt(row -> (int) row.stream()
                        .filter(box -> box.getDice() != null)
                        .map(box -> box.getDice().getDiceColor())
                        .filter(box -> box.equals(color)).count())
                .reduce(0, (sum, x) -> sum + x);
    }
}

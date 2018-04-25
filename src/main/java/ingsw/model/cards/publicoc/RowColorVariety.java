package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.List;

/**
 * Public Objective Card that counts the columns with non-repeated colors
 */
public class RowColorVariety extends PublicObjectiveCard {

    /**
     * Create a Row Color Variety instance. Calls <code>super()</code> with parameters the card name "RowColorVariety" and
     * 6 that represents its points.
     */
    public RowColorVariety() {
        super("RowColorVariety", 6);
    }

    /**
     * Check in every row if every dices has different color in the row
     * @param grid Grid to check
     * @return the number of columns that respect the condition
     */
    @Override
    public int check(List<List<Box>> grid) {
        return (int) grid.stream()
                .filter(x -> x.stream()
                        .filter(box -> box.getDice() != null)
                        .map(y -> y.getDice().getDiceColor())
                        .filter(y -> !y.equals(Color.BLANK))
                        .distinct().count() == 5)
                .count();
    }
}

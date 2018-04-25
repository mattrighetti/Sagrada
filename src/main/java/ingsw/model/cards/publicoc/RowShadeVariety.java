package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

/**
 * Public Objective Card that counts the columns with non-repeated shades
 */
public class RowShadeVariety extends PublicObjectiveCard {

    /**
     * Create a Row Shade Variety instance. Calls <code>super()</code> with parameters the card name "RowShadeVariety" and
     * 5 that represents its points.
     */
    public RowShadeVariety() {
        super("RowShadeVariety", 5);
    }

    /**
     * Check in every row if every dices has different value in the row
     * @param grid Grid to check
     * @return the number of rows that respect the condition
     */
    @Override
    public int check(List<List<Box>> grid) {
        return (int) grid.stream()
                            .filter(boxList -> boxList.stream()
                            .filter(box -> box.getDice() != null)
                            .map(box -> box.getDice().getFaceUpValue())
                            .filter(integer -> integer > 0).distinct().count() == 5 ).count();
    }
}

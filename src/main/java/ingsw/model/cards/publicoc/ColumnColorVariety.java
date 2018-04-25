package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.LinkedList;
import java.util.List;

/**
 * Public Objective Card that counts how many column with non-repeated colors are in the grid.
 */
public class ColumnColorVariety extends PublicObjectiveCard {

    /**
     * Create a Column Color Variety instance. Calls <code>super()</code> with parameters the card name "ColumnColorVariety" and
     * 1 that represents its points
     */
    public ColumnColorVariety() {
        super("ColumnColorVariety", 1);
    }

    /**
     * Check in every column if every dices has different color in the column
     * @param grid Grid to check
     * @return The number of columns that respect the condition
     */
    @Override
    public int check(List<List<Box>> grid) {
        int minValue = 0;
        List<Color> colorList;
        for (int i = 0; i < 5; i++) {
            colorList = new LinkedList<>();
            for (int j = 0; j < 4; j++)
                if (grid.get(j).get(i).getDice() != null && !colorList.contains(grid.get(j).get(i).getDice().getDiceColor()))
                    colorList.add(grid.get(j).get(i).getDice().getDiceColor());
            if (colorList.size() == 4)
                minValue++;
        }
        return minValue;
    }
}

package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.LinkedList;
import java.util.List;

/**
 * Public Objective Cards that counts the column in which there are no repeated dice
 */
public class ColumnShadeVariety extends PublicObjectiveCard {

    /**
     * Create a Column Shade Variety instance. Calls <code>super()</code> with parameters the card name "ColumnShadeVariety" and
     * 4 that represents its points
     */
    public ColumnShadeVariety() {
        super("ColumnShadeVariety", 4);
    }

    /**
     * Check in every column if every dices has different value in the column
     * @param grid The grid you want to check
     * @return The number of columns that respect the condition
     */
    @Override
    public int check(List<List<Box>> grid) {
        int minValue = 0;

        List<Integer> colorList;

        for (int i = 0; i < 5; i++) {
            colorList = new LinkedList<>();
            for (int j = 0; j < 4; j++)
                if (grid.get(j).get(i).getDice() != null && !colorList.contains(grid.get(j).get(i).getDice().getFaceUpValue()))
                    colorList.add(grid.get(j).get(i).getDice().getFaceUpValue());

            if (colorList.size() == 4)
                minValue++;
        }

        return minValue;
    }
}

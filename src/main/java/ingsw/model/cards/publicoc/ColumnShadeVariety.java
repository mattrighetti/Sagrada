package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.LinkedList;
import java.util.List;

public class ColumnShadeVariety extends PublicObjectiveCard {

    public ColumnShadeVariety() {
        super("ColumnShadeVariety", 4);
    }

    /**
     * Check in every column if every dices has different value in the column
     * @param grid
     * @return the number of columns that respect the condition
     */
    @Override
    public int check(List<List<Box>> grid) {
        int minValue = 0;

        List<Integer> colorList = new LinkedList<>();

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 4; j++) {
                if (grid.get(j).get(i).getDice() != null && !colorList.contains(grid.get(j).get(i).getDice().getFaceUpValue())) {
                    colorList.add(grid.get(j).get(i).getDice().getFaceUpValue());
                }
            }

            if (colorList.size() == 4)
                minValue++;

            colorList.clear();
        }

        return minValue;
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

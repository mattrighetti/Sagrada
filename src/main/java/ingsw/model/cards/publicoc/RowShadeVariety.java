package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class RowShadeVariety extends PublicObjectiveCard {

    public RowShadeVariety() {
        super("RowShadeVariety", 5);
    }

    /**
     * Checkin every row if every dices has different value in the row
     * @param grid
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

    /**
     * @param grid
     * @return the points gained with this card
     */
    @Override
    public int getScore(List<List<Box>> grid) {
        return getPoints() * check(grid);
    }
}

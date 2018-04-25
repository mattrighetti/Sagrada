package ingsw.model.cards.privateoc;

import ingsw.model.Color;
import ingsw.model.cards.Card;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.patterncard.PatternCard;

import java.util.List;

public class PrivateObjectiveCard extends Card {

    private Color color;

    public PrivateObjectiveCard(Color color) {
        super(color.toString());
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "PrivateObjCard{'" + getName() +
                "'}";
    }

    /**
     * Count how many dice of the same color relatedto this PrivateCard are in the grid
     * @param grid Grid to check
     * @return the number of occurrences
     */
    public int check(List<List<Box>> grid) {
        return grid.stream().
                mapToInt(row -> (int) row.stream().filter(box -> box.getDice() != null).map( box -> box.getDice().getDiceColor()).filter(box -> box.equals(color)).count()).reduce(0, (sum,x) -> sum + x );
    }
}

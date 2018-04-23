package ingsw.model.cards.privateoc;

import ingsw.model.Color;
import ingsw.model.cards.Card;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.patterncard.PatternCard;

import java.util.List;

public class PrivateObjectiveCard extends Card {

    private Color color;

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "PrivateObjCard{" +
                "name='" + getName() + '\'' +
                '}';
    }

    public PrivateObjectiveCard(Color color) {
        super(color.toString());
        this.color = color;
    }

    //Count how many dice of the same color of the PrivateCard there are in the pattern and \return the value
    public int check(List<List<Box>> grid) {
        return grid.stream().
                mapToInt(x -> (int) x.stream().map( y -> y.getColor()).filter(y -> y.equals(color)).count()).reduce(0, (sum,x) -> sum + x );
    }
}

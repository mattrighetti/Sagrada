package ingsw.model.cards.privateoc;

import ingsw.model.Color;
import ingsw.model.cards.Card;
import ingsw.model.cards.patterncard.PatternCard;

public class PrivateObjectiveCard extends Card {

    private Color color;

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
    public int check(PatternCard patternCard) {
        return 0; //TODO
    }
}

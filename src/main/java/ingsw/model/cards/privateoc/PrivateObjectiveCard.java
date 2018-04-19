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

    public PrivateObjectiveCard(String name, Color color) {
        super(name);
        this.color = color;
    }

    //Count how many dice of the same color of the PrivateCard there are in the pattern and \return the value
    public int check(PatternCard patternCard){
        int noDices = 0;
        for (int i = 0; i < 4; i++ ){
            for (int j = 0; j< 5; j++ ) if (patternCard.getGrid()[i][j].getColor().equals(color)) noDices++;
        }
        return noDices;
    }
}

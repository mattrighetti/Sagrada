package ingsw.model.cards.publicoc;

import ingsw.model.cards.Card;
import ingsw.model.cards.patterncard.Box;

import java.util.List;

public abstract class PublicObjectiveCard extends Card {

    private int points;

    public PublicObjectiveCard(String name, int points) {
        super(name);
        this.points = points;
    }

    @Override
    public String toString() {
        return "PublicObjCard{" +
                "'" + getName() + "'" +
                '}';
    }

    public int getPoints() {
        return points;
    }

    public abstract int check(List<List<Box>> grid);
}

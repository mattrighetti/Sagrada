package ingsw.model.cards.publicoc;

import ingsw.model.cards.Card;

public abstract class PublicObjectiveCard extends Card {
    private int points;

    public PublicObjectiveCard(String name) {
        super(name);
        //TODO points
    }

    @Override
    public String toString() {
        return "PublicObjCard{" +
                "'" + getName() + "'" +
                '}';
    }

    public abstract void check();
}

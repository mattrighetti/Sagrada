package ingsw.model.cards.privateoc;

import ingsw.model.cards.Card;

public abstract class PrivateObjectiveCard extends Card {

    @Override
    public String toString() {
        return "PrivateObjCard{" +
                "name='" + getName() + '\'' +
                '}';
    }

    public PrivateObjectiveCard(String name) {
        super(name);
    }

    public abstract void check();
}

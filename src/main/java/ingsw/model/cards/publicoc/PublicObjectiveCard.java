package ingsw.model.cards.publicoc;

import ingsw.model.cards.Card;

public abstract class PublicObjectiveCard extends Card {

    private short points;

    public abstract void check();
}

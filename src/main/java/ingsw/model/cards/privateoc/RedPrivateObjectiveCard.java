package ingsw.model.cards.privateoc;

import ingsw.model.Color;

public class RedPrivateObjectiveCard extends PrivateObjectiveCard {
    private Color cardColor = Color.RED;

    public RedPrivateObjectiveCard() {
        super("Red");
    }

    public Color getCardColor() {
        return cardColor;
    }
}

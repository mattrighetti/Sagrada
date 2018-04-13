package ingsw.model.cards.privateoc;

import ingsw.model.Color;

public class YellowPrivateObjectiveCard extends PrivateObjectiveCard {
    private Color cardColor = Color.YELLOW;

    public YellowPrivateObjectiveCard() {
        super("Yellow");
    }

    public Color getCardColor() {
        return cardColor;
    }
}

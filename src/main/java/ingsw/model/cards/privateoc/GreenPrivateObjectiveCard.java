package ingsw.model.cards.privateoc;

import ingsw.model.Color;

public class GreenPrivateObjectiveCard extends PrivateObjectiveCard {
    private Color color = Color.GREEN;

    public GreenPrivateObjectiveCard() {
        super("Green");
    }

    public Color getColor() {
        return color;
    }
}

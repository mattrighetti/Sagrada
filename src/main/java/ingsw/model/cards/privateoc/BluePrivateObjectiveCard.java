package ingsw.model.cards.privateoc;

import ingsw.model.Color;

public class BluePrivateObjectiveCard extends PrivateObjectiveCard {
    private Color color = Color.BLUE;

    public BluePrivateObjectiveCard() {
        super("Blue");
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void check() {
        //TODO
    }
}

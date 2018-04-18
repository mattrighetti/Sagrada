package ingsw.model.cards.privateoc;

import ingsw.model.Color;

public class PurplePrivateObjectiveCard extends PrivateObjectiveCard {
    private Color color = Color.PURPLE;

    public PurplePrivateObjectiveCard() {
        super("Purple");
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void check() {
        //TODO
    }
}

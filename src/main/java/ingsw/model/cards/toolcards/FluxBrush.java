package ingsw.model.cards.toolcards;

import ingsw.model.GameManager;

public class FluxBrush extends ToolCard {

    public FluxBrush() {
        super("FluxBrush");
    }

    /**
     * After drafting, re-roll the drafted dice.
     * if it cannot be placed, return it to the Draft pool.
     */
    @Override
    public void action(GameManager gameManager) {

    }
}

package ingsw.model.cards.toolcards;

import ingsw.model.GameManager;

public class GlazingHammer extends ToolCard {

    public GlazingHammer() {
        super("GlazingHammer");
    }

    /**
     * Re-roll all dice in the Draft pool.
     * This may only be used on your second turn before drafting.
     */
    @Override
    public void action(GameManager gameManager) {
        gameManager.glazingHammerResponse();
    }
}

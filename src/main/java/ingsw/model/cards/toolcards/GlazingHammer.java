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
        if (gameManager.getTurnInRound() == 2) {
            gameManager.getCurrentRound().getCurrentPlayer().decreaseFavorTokens(getPrice());
            gameManager.glazingHammerResponse();
        }
        else
            gameManager.avoidToolCardUse();

        gameManager.toolCardLock.set(false);
    }
}

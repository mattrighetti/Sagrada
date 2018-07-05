package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.FluxBrushResponse;
import ingsw.model.Dice;
import ingsw.model.GameManager;

import java.rmi.RemoteException;
import java.util.List;

public class FluxBrush extends ToolCard {

    private List<Dice> temporaryDraftedDice;

    /**
     * Creates a new FluxBrush tool card
     */
    public FluxBrush() {
        super("FluxBrush");
    }

    /**
     * After drafting, re-roll the drafted dice.
     * if it cannot be placed, return it to the Draft pool.
     */
    @Override
    public void action(GameManager gameManager) {
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxBrushResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        waitForToolCardAction(gameManager);

        if (gameManager.getToolCardLock().get()) {

            gameManager.fluxBrushResponse();
            gameManager.getCurrentRound().getCurrentPlayer().decreaseFavorTokens(getPrice());
            gameManager.getCurrentRound().toolCardMoveDone();
            gameManager.getToolCardLock().set(false);
        }
        System.out.println("end FluxBrush");
    }

    public List<Dice> getTemporaryDraftedDice() {
        return temporaryDraftedDice;
    }

    public void setTemporaryDraftedDice(List<Dice> temporaryDraftedDice) {
        this.temporaryDraftedDice = temporaryDraftedDice;
    }

}

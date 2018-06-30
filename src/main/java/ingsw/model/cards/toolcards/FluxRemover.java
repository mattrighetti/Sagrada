package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.FluxRemoverResponse;
import ingsw.model.Dice;
import ingsw.model.GameManager;

import java.rmi.RemoteException;
import java.util.List;

public class FluxRemover extends ToolCard {

    private List<Dice> draftedDice;
    private Dice diceFromBag;

    public void setDiceFromBag(Dice diceFromBag) {
        this.diceFromBag = diceFromBag;
    }

    public Dice getDiceFromBag() {

        return diceFromBag;
    }

    public FluxRemover() {
        super("FluxRemover");
    }

    /**
     * After drafting,return the die to the Dice Bag and pull one die from the bag.
     * Choose a value and place the new dice, obeying all the placement restrictions, or return it to the Draft pool.
     */
    @Override
    public void action(GameManager gameManager) {
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxRemoverResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        waitForToolCardAction(gameManager);

        if (!gameManager.toolCardLock.get())
            return;

        gameManager.fluxRemoverResponse();
        gameManager.getCurrentRound().toolCardMoveDone();
        gameManager.toolCardLock.set(false);
    }

    public void setDraftedDice(List<Dice> draftedDice) {
        this.draftedDice = draftedDice;
    }


    public List<Dice> getDraftedDice() {
        return draftedDice;
    }

}

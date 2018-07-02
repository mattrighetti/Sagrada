package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.GrindingStoneResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class GrindingStone extends ToolCard {

    public GrindingStone() {
        super("GrindingStone");
    }

    /**
     * After drafting, flip the dice to the opposite side.
     */
    @Override
    public void action(GameManager gameManager) {
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new GrindingStoneResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        waitForToolCardAction(gameManager);

        if (gameManager.toolCardLock.get()) {
            gameManager.getCurrentRound().getCurrentPlayer().decreaseFavorTokens(getPrice());
            gameManager.grindingStoneResponse();
            gameManager.getCurrentRound().toolCardMoveDone();
            gameManager.toolCardLock.set(false);
        }
    }
}

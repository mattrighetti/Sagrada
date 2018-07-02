package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.EglomiseBrushResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class EglomiseBrush extends ToolCard {

    public EglomiseBrush() {
        super("EglomiseBrush");
    }

    /**
     * Move any one die your window ignoring the color restrictions.
     * You must obey all other placement restrictions.
     */
    @Override
    public void action(GameManager gameManager) {
        if (gameManager.getCurrentRound().getCurrentPlayer().getPatternCard().getNoOfDice() > 0) {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new EglomiseBrushResponse(gameManager.getCurrentRound().getCurrentPlayer().getPatternCard().computeAvailablePositionsNoColor()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            waitForToolCardAction(gameManager);

            if (gameManager.toolCardLock.get()) {
                gameManager.eglomiseBrushResponse();
                gameManager.getCurrentRound().getCurrentPlayer().decreaseFavorTokens(getPrice());
                gameManager.getCurrentRound().toolCardMoveDone();
                gameManager.toolCardLock.set(false);
            }
        } else {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}

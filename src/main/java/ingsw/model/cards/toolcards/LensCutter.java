package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.LensCutterResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class LensCutter extends ToolCard {

    public LensCutter() {
        super("LensCutter");
    }

    /**
     * After drafting, swap the drafted dice with a dice from the round track.
     */
    @Override
    public void action(GameManager gameManager) {
        if (gameManager.getNoOfCurrentRound() > 1) {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LensCutterResponse());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            waitForToolCardAction(gameManager);

            if (gameManager.toolCardLock.get()) {
                gameManager.getCurrentRound().getCurrentPlayer().decreaseFavorTokens(getPrice());
                gameManager.lensCutterResponse();
                gameManager.getCurrentRound().toolCardMoveDone();
                gameManager.toolCardLock.set(false);
            }
        } else {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse());
                gameManager.toolCardLock.set(false);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}

package ingsw.model.cards.toolcards;

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
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LensCutterResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        waitForToolCardAction(gameManager);

        gameManager.lensCutterResponse();
    }
}

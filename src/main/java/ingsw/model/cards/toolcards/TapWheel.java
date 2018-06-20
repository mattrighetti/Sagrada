package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.TapWheelResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class TapWheel extends ToolCard {

    public TapWheel() {
        super("TapWheel");
    }

    /**
     * Move up to two dice of the same color that match the color of a dice on the Round Track.
     * You must obey all the placement restrictions.
     */
    @Override
    public void action(GameManager gameManager) {

        if (gameManager.getCurrentRound().getCurrentPlayer().getPatternCard().isGridEmpty()) {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse(gameManager.getCurrentRound().getCurrentPlayer().getFavourTokens()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            gameManager.getCurrentRound().toolCardMoveDone();
            return;
        }

        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new TapWheelResponse(0));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            if (!gameManager.getdoubleMove()) {
                waitForToolCardAction(gameManager);
            } else break;
        }

        System.out.println("end tapwheel");
    }
}

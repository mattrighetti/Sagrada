package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.CopperFoilBurnisherResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class CopperFoilBurnisher extends ToolCard {

    public CopperFoilBurnisher() {
        super("CopperFoilBurnisher");
    }

    /**
     * Move any dice in your window ignoring shade restriction.
     */
    @Override
    public void action(GameManager gameManager) {
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new CopperFoilBurnisherResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        synchronized (gameManager.toolCardLock) {
            try {
                gameManager.toolCardLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        gameManager.copperFoilBurnisherResponse();
    }


}

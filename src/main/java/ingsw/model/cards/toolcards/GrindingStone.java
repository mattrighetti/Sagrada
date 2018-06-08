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

        synchronized (gameManager.toolCardLock) {
            try {
                gameManager.toolCardLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        gameManager.grindingStoneResponse();
        gameManager.getCurrentRound().hasMadeAMove();


    }
}

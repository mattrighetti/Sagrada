package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.CopperFoilBurnisherResponse;
import ingsw.model.Dice;
import ingsw.model.GameManager;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.patterncard.PatternCard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
            PatternCard patternCard = gameManager.getCurrentRound().getCurrentPlayer().getPatternCard();
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new CopperFoilBurnisherResponse(patternCard.computeAvailablePositionsNoValue()));
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

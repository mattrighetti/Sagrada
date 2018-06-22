package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.CopperFoilBurnisherResponse;
import ingsw.model.GameManager;
import ingsw.model.cards.patterncard.PatternCard;

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
        if(gameManager.getCurrentRound().getCurrentPlayer().getPatternCard().getNoOfDice() > 0) {
            try {
                PatternCard patternCard = gameManager.getCurrentRound().getCurrentPlayer().getPatternCard();
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new CopperFoilBurnisherResponse(patternCard.computeAvailablePositionsNoValue()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            waitForToolCardAction(gameManager);
            gameManager.copperFoilBurnisherResponse();
            gameManager.getCurrentRound().hasMadeAMove();
        } else {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


}

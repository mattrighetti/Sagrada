package ingsw.model.cards.toolcards;

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
     *
     * First it controls if Tool card can be used
     * Then it sends to the client the Copper Foil Burnisher tool card response containing the available positions
     * without shades restrictions.
     * Wait the end of the tool card move
     * Finally it calls the response method in game manager to send the new data to the client
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

            if (gameManager.toolCardLock.get()) {
                gameManager.copperFoilBurnisherResponse();
                gameManager.getCurrentRound().toolCardMoveDone();
                gameManager.toolCardLock.set(false);
            }
        } else {
            gameManager.avoidToolCardUse();
        }
    }


}

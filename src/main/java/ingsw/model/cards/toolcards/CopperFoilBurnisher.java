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

    private List<Dice> buildListDice(GameManager gameManager){
        PatternCard patternCard = gameManager.getCurrentRound().getCurrentPlayer().getPatternCard();
        List<Dice> diceToCheck = new ArrayList<>();
        for (Dice dice : gameManager.getDraftedDice()) {
            diceToCheck.add(dice);
        }
        for (int i = 0; i < patternCard.getGrid().size() ; i++) {
            for (int j = 0; j < patternCard.getGrid().get(i).size(); j++) {
                if (patternCard.getGrid().get(i).get(j).getDice() != null)
                    diceToCheck.add(patternCard.getGrid().get(i).get(j).getDice());
            }
        }
        return diceToCheck;
    }


}

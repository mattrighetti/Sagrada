package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.TapWheelResponse;
import ingsw.model.Dice;
import ingsw.model.GameManager;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

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


        int diceInRoundTrack = gameManager.getRoundTrack().stream()
                .mapToInt(List::size)
                .reduce(0, (sum,x) -> sum + x);

        if (gameManager.getCurrentRound().getCurrentPlayer().getPatternCard().isGridEmpty() || (diceInRoundTrack < 1)) {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse(gameManager.getCurrentRound().getCurrentPlayer().getFavourTokens()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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

                if (!gameManager.toolCardLock.get())
                    return;

            } else break;
        }

        System.out.println("end tapwheel");
        gameManager.toolCardLock.set(false);
    }
}

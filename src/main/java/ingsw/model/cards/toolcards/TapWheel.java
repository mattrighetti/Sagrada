package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.TapWheelResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;
import java.util.List;

public class TapWheel extends ToolCard {

    /**
     * Creates a new TapWheel tool card
     */
    public TapWheel() {
        super("TapWheel");
    }

    /**
     * Move up to two dice of the same color that match the color of a dice on the Round Track.
     * You must obey all the placement restrictions.
     * There is also a possibility to do a double move(swapping two dice) during the first dice placement.
     * In that case the tool card ends immediately.
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

        gameManager.getCurrentRound().getCurrentPlayer().decreaseFavorTokens(getPrice());

        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new TapWheelResponse(0));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            if (!gameManager.getdoubleMove()) {
                waitForToolCardAction(gameManager);

                if (!gameManager.getToolCardLock().get())
                    return;

            } else break;
        }

        System.out.println("end tapwheel");
        gameManager.getToolCardLock().set(false);
    }
}

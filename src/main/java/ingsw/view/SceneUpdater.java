package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.controller.network.commands.PatternCardNotification;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.DoubleString;

import java.util.List;

public interface SceneUpdater {

    default void updateConnectedUsers(int usersConnected) {
        System.out.println("Not overridden");
    }

    default void setNetworkType(NetworkType clientController) {
        System.out.println("Not overridden");
    }

    default void updateExistingMatches(List<DoubleString> matches) {
        System.out.println("Not overridden");
    }

    default void setPatternCards(List<PatternCard> patternCards) {
        System.out.println("Not overridden");
    }

    default void launchSecondGui(String username) {
        System.out.println("Not overridden");
    }

    default void launchAlert() {
        System.out.println("Not overridden");
    }

    default void closeStage() {
        System.out.println("Not overridden");
    }

    default void launchThirdGui(PatternCardNotification patternCardNotification) {
        System.out.println("Not overridden");
    }


    default void loadData(BoardDataResponse boardDataResponse) {
        System.out.println("Not overridden");
    }

    default void launchFourthGui(BoardDataResponse boardDataResponse) {
        System.out.println("Not overridden");
    }

    default void popUpDraftNotification() {
        System.out.println("Not overridden");
    }

    default void setDraftedDice(List<Dice> dice) {
        System.out.println("Not overridden");
    }
}

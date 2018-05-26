package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.controller.network.commands.PatternCardNotification;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.DoubleString;

import java.util.List;

public interface SceneUpdater {

    void updateConnectedUsers(int usersConnected);

    void setNetworkType(NetworkType clientController);

    void updateExistingMatches(List<DoubleString> matches);

    default void setPatternCards(List<PatternCard> patternCards) {

    }

     default void launchSecondGui() {

     }

    default void launchAlert() {

    }

    default void closeStage() {

    }

    default void launchThirdGui(PatternCardNotification patternCardNotification) {

    }


    default void loadData(BoardDataResponse boardDataResponse) {
        System.out.println("I'm not in GameController");
    }

    default void launchFourthGui(BoardDataResponse boardDataResponse) {

    }
}

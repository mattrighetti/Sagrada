package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.DoubleString;

import java.io.IOException;
import java.util.List;

public interface SceneUpdater {

    void updateConnectedUsers(int usersConnected);

    void setNetworkType(NetworkType clientController);

    void updateExistingMatches(List<DoubleString> matches);

    default void launchThirdGui() throws IOException {

    }

    default void setPatternCards(List<PatternCard> patternCards) {

    }
}

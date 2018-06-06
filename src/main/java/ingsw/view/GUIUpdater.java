package ingsw.view;

import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.controller.network.commands.PatternCardNotification;
import ingsw.utilities.DoubleString;

import java.util.List;

public interface GUIUpdater {
    void deploySocketClient();

    void deployRMIClient();

    void launchSecondGUI(String username);

    void launchThirdGUI(PatternCardNotification patternCardNotification);

    void launchFourthGUI(BoardDataResponse boardDataResponse);

    void updateConnectedUsers(int connectedUsers);

    void updateExistingMatches(List<DoubleString> matches);

    void changeToRMI();

    void changeToSocket();

    String getUsername();

    void closeApplication();
}

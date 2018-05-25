package ingsw.view;

import ingsw.controller.network.commands.PatternCardNotification;
import ingsw.utilities.DoubleString;

import java.io.IOException;
import java.util.List;

public interface GUIUpdater {
    void launchSecondGUI();

    void launchThirdGUI(PatternCardNotification patternCardNotification);

    void launchFourthGUI();

    void updateConnectedUsers(int connectedUsers);

    void updateExistingMatches(List<DoubleString> matches);

    void setUsername(String username);

    void changeToRMI();

    void changeToSocket();

    String getUsername();

    void closeApplication();
}

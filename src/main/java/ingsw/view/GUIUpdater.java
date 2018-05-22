package ingsw.view;

import ingsw.utilities.DoubleString;

import java.io.IOException;
import java.util.List;

public interface GUIUpdater {
    void launchSecondGUI() throws IOException;

    void launchThirdGUI() throws IOException;

    void launchFourthGUI() throws IOException;

    void updateConnectedUsers(int connectedUsers);

    void updateExistingMatches(List<DoubleString> matches);

    void setUsername(String username);

    void changeToRMI();

    void changeToSocket();

    String getUsername();
}
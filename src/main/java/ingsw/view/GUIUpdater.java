package ingsw.view;

import java.io.IOException;

public interface GUIUpdater {
    void launchSecondGUI() throws IOException;

    void launchThirdGUI() throws IOException;

    void launchFourthGUI() throws IOException;

    void updateConnectedUsers(int connectedUsers);

    void setUsername(String username);

    void changeToRMI();

    void changeToSocket();

    String getUsername();
}

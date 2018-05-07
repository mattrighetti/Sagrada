package ingsw.view;

import java.io.IOException;

public interface GUIUpdater {
    void launchSecondGUI() throws IOException;

    void launchThirdGUI() throws IOException;

    void updateConnectedUsers(int connectedUsers);
}

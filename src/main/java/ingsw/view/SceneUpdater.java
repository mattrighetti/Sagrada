package ingsw.view;

import ingsw.controller.network.NetworkType;

public interface SceneUpdater {
    void updateConnectedUsers(int usersConnected);
    void setNetworkType(NetworkType clientController);

    void updateExistingMatches(String matchName);
}

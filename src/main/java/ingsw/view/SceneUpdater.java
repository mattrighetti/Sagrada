package ingsw.view;

import ingsw.controller.network.NetworkType;

public interface SceneUpdater {
    void updateConnectedUsers(int usersConnected);
    void setClientController(NetworkType clientController);
}

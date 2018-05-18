package ingsw.view;

import ingsw.controller.network.NetworkType;

public interface SceneUpdater {
    void updateConnectedUsers(int usersConnected);

    void setNetworkType(NetworkType clientController);

    default void updateExistingMatches(String matchName) throws NoSuchMethodException {
        throw new NoSuchMethodException(this.getClass() + " :Class does not implement this method");
    }
}

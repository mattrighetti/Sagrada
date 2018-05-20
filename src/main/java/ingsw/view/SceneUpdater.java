package ingsw.view;

import ingsw.controller.network.NetworkType;

public interface SceneUpdater {
    default void updateConnectedUsers(int usersConnected) throws NoSuchMethodException {
        throw new NoSuchMethodException(this.getClass() + " :Class does not implement this method");
    }

    default void setNetworkType(NetworkType clientController) throws NoSuchMethodException {
        throw new NoSuchMethodException(this.getClass() + " :Class does not implement this method");
    }

    default void updateExistingMatches(String matchName) throws NoSuchMethodException {
        throw new NoSuchMethodException(this.getClass() + " :Class does not implement this method");
    }
}

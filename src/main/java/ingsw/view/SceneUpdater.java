package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.utilities.DoubleString;

import java.util.List;

public interface SceneUpdater {

    void updateConnectedUsers(int usersConnected);

    void setNetworkType(NetworkType clientController);

    void updateExistingMatches(List<DoubleString> matches);
}

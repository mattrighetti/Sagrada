package ingsw.view;


import ingsw.controller.network.NetworkType;

public class CLI implements SceneUpdater {

    public static void main(String[] args) {

    }


    @Override
    public void updateConnectedUsers(int usersConnected) {
        System.out.println("Connected Users: " + usersConnected);
    }

    @Override
    public void setNetworkType(NetworkType clientController) {

    }

    @Override
    public void updateExistingMatches(String matchName) throws NoSuchMethodException {

    }
}

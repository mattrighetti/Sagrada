package ingsw.controller.network.rmi;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.ResponseHandler;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.User;

import java.rmi.Remote;

public class RMIUserObserver implements Remote, UserObserver {
    private ResponseHandler rmiController;

    public RMIUserObserver(RMIController rmiController) {
        this.rmiController = rmiController;
    }

    @Override
    public void onJoin(int numberOfConnectedUsers) {

    }

    @Override
    public void sendMessage(Message message) {

    }
}

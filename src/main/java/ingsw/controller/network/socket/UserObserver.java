package ingsw.controller.network.socket;

import ingsw.controller.network.Message;

public interface UserObserver {

    void onJoin(int numberOfConnectedUsers);

    void sendMessage(Message message);
}

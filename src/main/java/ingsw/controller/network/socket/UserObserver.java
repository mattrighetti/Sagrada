package ingsw.controller.network.socket;

import ingsw.controller.network.Message;
import ingsw.model.User;

public interface UserObserver {
    void onJoin(User user);

    void onJoin(int numberOfConnectedUsers);

    void sendMessage(Message message);
}

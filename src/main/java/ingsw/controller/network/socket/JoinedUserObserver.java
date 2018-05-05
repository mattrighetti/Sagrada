package ingsw.controller.network.socket;

import ingsw.model.User;

public interface JoinedUserObserver {
    void onJoin(User user);

    void onJoin(int numberOfConnectedUsers);
}

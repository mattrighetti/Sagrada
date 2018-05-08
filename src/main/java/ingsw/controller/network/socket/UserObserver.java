package ingsw.controller.network.socket;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.DiceNotification;
import ingsw.model.User;

public interface UserObserver {
    void onJoin(User user);

    void onJoin(int numberOfConnectedUsers);

    void sendMessage(Message message);

    void receiveDraftNotification();

    void sendResponse(DiceNotification diceNotification);
}

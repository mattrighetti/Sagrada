package ingsw.controller.network.commands;

import ingsw.utilities.NotificationType;

public class Notification implements Response {
    public NotificationType notificationType;

    public Notification(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

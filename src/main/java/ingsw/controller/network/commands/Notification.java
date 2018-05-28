package ingsw.controller.network.commands;

import ingsw.utilities.NotificationType;

public class Notification implements Response {
    public NotificationType notificationType;

    public Notification(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    /**
     * Method that returns a Response after the Request has been properly handled
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

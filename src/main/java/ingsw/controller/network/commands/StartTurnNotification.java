package ingsw.controller.network.commands;

import ingsw.utilities.NotificationType;

import java.util.Map;

public class StartTurnNotification extends Notification {
    public Map<String,Boolean[][]> booleanMapGrid;

    public StartTurnNotification(Map<String,Boolean[][]> booleanMapGrid) {
        super(NotificationType.START_TURN);

        this.booleanMapGrid = booleanMapGrid;
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

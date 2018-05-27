package ingsw.controller.network.commands;

import ingsw.utilities.NotificationType;

import java.util.List;

public class StartTurnNotification extends Notification {

    public List<Boolean[][]> booleanListGrid;

    public StartTurnNotification(List<Boolean[][]> booleanListGrid) {
        super(NotificationType.START_TURN);

        this.booleanListGrid = booleanListGrid;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

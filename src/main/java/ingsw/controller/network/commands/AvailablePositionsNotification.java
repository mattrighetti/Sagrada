package ingsw.controller.network.commands;

import java.util.List;

public class AvailablePositionsNotification extends Notification {

    public List<Boolean[][]> availablePositions;

    public AvailablePositionsNotification(List<Boolean[][]> availablePositions) {
        this.availablePositions = availablePositions;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {

        //TODO return responseHandler.handle(this);
    }
}

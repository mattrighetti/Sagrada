package ingsw.controller.network.commands;

import ingsw.utilities.NotificationType;

import java.util.List;

public class AvailablePositionsNotification extends Notification {

    public List<Boolean[][]> availablePositions;

    public AvailablePositionsNotification(List<Boolean[][]> availablePositions) {
        super(NotificationType.DRAFT_DICE);
        this.availablePositions = availablePositions;
    }

    /**
     * Method that declares by which this response should be handled
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {

        //TODO return responseHandler.handle(this);
    }
}

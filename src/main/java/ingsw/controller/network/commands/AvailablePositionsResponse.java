package ingsw.controller.network.commands;

import java.util.Map;

public class AvailablePositionsResponse implements Response {

    public Map<String, Boolean[][]> availablePositions;

    public AvailablePositionsResponse(Map<String, Boolean[][]> availablePositions) {
        this.availablePositions = availablePositions;
    }

    /**
     * Method that declares by which this response should be handled
     *
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

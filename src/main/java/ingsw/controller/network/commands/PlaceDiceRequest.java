package ingsw.controller.network.commands;

import ingsw.model.Dice;

public class PlaceDiceRequest implements Request {
    public Dice dice;
    public int columnIndex;
    public int rowIndex;

    public PlaceDiceRequest(Dice dice, int columnIndex, int rowIndex) {
        this.dice = dice;
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    /**
     * Method that returns a Response after the Request has been properly handled
     * @param requestHandler class that handles the Request
     * @return corresponding Response
     */
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}

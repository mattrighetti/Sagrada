package ingsw.controller.network.commands;

import ingsw.utilities.DoubleString;

import java.util.List;

public class CreateMatchResponse implements Response {
    public List<DoubleString> doubleString;

    public CreateMatchResponse(List<DoubleString> doubleString) {
        this.doubleString = doubleString;
    }

    /**
     * Method that declares by which this response should be handled
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

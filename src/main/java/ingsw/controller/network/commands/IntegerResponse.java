package ingsw.controller.network.commands;

public class IntegerResponse implements Response {
    public int number;

    public IntegerResponse(int number) {
        this.number = number;
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

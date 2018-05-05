package ingsw.controller.network.commands;

public class IntegerResponse implements Response {
    public int number;

    public IntegerResponse(int number) {
        this.number = number;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

package ingsw.controller.network.commands;

import java.util.List;

public class FinishedMatchesResponse implements Response {
    public List<String> finishedMatchesList;

    public FinishedMatchesResponse(List<String> finishedMatchesList) {
        this.finishedMatchesList = finishedMatchesList;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

package ingsw.controller.network.commands;

import ingsw.utilities.TripleString;

import java.util.List;

public class RankingDataResponse implements Response {
    public List<TripleString> tripleString;

    public RankingDataResponse(List<TripleString> tripleString) {
        this.tripleString = tripleString;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}

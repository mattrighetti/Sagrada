package ingsw.controller.network.commands;

import ingsw.utilities.DoubleString;
import ingsw.utilities.TripleString;

import java.util.List;
import java.util.Map;

public class BundleDataResponse implements Response {
    public int connectedUsers;
    public List<TripleString> rankings;
    public List<DoubleString> matches;
    public Map<String, TripleString> userStatistics;

    public BundleDataResponse(int connectedUsers, List<TripleString> rankings, List<DoubleString> matches, Map<String, TripleString> userStatistics) {
        this.connectedUsers = connectedUsers;
        this.rankings = rankings;
        this.matches = matches;
        this.userStatistics = userStatistics;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }

    public TripleString getUserStatsData(String username) {
        return userStatistics.get(username);
    }
}

package ingsw.controller.network.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ingsw.utilities.MoveStatus;

import java.util.ArrayList;
import java.util.List;

public class HistoryResponse implements Response {
    public List<MoveStatus> historyJSON;

    public HistoryResponse(String historyJSON) {
        convertFromJSON(historyJSON);
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }

    private void convertFromJSON(String historyJSON) {
        Gson gson = new Gson();
        this.historyJSON = gson.fromJson(historyJSON, new TypeToken<ArrayList<MoveStatus>>(){}.getType());
    }
}

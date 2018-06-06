package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

import java.util.Map;

public class LathekinResponse extends UseToolCardResponse {

    public Map<String, Boolean[][]> availablePositions;

    public LathekinResponse(Map<String, Boolean[][]> availablePositions) {
        super(ToolCardType.LATHEKIN);
        this.availablePositions = availablePositions;
    }
}

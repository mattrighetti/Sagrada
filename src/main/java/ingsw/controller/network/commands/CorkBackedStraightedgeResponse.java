package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

import java.util.Map;


public class CorkBackedStraightedgeResponse extends UseToolCardResponse {

    public Map<String, Boolean[][]> availablePositions;

    public CorkBackedStraightedgeResponse(Map<String, Boolean[][]> availablePositions) {
        super(ToolCardType.CORK_BACKED_STRAIGHT_EDGE);
        this.availablePositions = availablePositions;
    }
}

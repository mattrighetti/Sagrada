package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

import java.util.Map;

public class EglomiseBrushResponse extends UseToolCardResponse {

    public Map<String,Boolean[][]> availablePositions;

    public EglomiseBrushResponse( Map<String,Boolean[][]> availablePositions) {
        super(ToolCardType.EGLOMISE_BRUSH);
        this.availablePositions = availablePositions;
    }
}

package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

import java.util.Map;

public class CopperFoilBurnisherResponse extends UseToolCardResponse {
    public Map<String,Boolean[][]> availablePositions;

    public CopperFoilBurnisherResponse(Map<String,Boolean[][]> availablePositions) {
        super(ToolCardType.COPPER_FOIL_BURNISHER);
        this.availablePositions = availablePositions;
    }
}
package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

import java.util.List;

public class CopperFoilBurnisherResponse extends UseToolCardResponse {

    public List<Boolean[][]> availablePositions;

    public CopperFoilBurnisherResponse(List<Boolean[][]> availablePositions) {
        super(ToolCardType.COPPER_FOIL_BURNISHER);
        this.availablePositions = availablePositions;
    }
}

package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;
import ingsw.utilities.Tuple;

public class CopperFoilBurnisherRequest extends MoveToolCardRequest {

    public Tuple dicePosition;
    public Tuple position;

    public CopperFoilBurnisherRequest(Tuple dicePosition, Tuple position) {
        super(ToolCardType.COPPER_FOIL_BURNISHER);
        this.dicePosition = dicePosition;
        this.position = position;
    }
}

package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

public class GrozingPliersResponse extends UseToolCardResponse {

    public String restrictions;

    public GrozingPliersResponse(String restrictions) {
        super(ToolCardType.GROZING_PLIERS);
        this.restrictions = restrictions;
    }
}

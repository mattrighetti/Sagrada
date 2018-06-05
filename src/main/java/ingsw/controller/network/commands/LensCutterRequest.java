package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

public class LensCutterRequest extends MoveToolCardRequest {

    public LensCutterRequest() {
        super(ToolCardType.LENS_CUTTER);
    }
}

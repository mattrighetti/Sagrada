package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

public class LensCutterRequest extends MoveToolCardRequest {
    public int roundIndex;
    public String roundTrackDice;
    public String poolDice;

    public LensCutterRequest(int roundIndex, String roundTrackDice, String poolDice) {
        super(ToolCardType.LENS_CUTTER);
        this.poolDice = poolDice;
        this.roundIndex = roundIndex;
        this.roundTrackDice = roundTrackDice;
    }
}

package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

import java.util.List;

public class RoundTrackToolCardResponse extends UseToolCardResponse {
    public List<List<Dice>> roundTrack;

    public RoundTrackToolCardResponse(List<List<Dice>> roundTrack) {
        super(ToolCardType.ROUND_TRACK);
        this.roundTrack = roundTrack;
    }
}

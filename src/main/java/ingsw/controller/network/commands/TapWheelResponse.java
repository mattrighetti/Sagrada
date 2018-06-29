package ingsw.controller.network.commands;

import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.ToolCardType;

import java.util.Map;

public class TapWheelResponse extends UseToolCardResponse {

    public int phase;
    public Map<String, Boolean[][]> availablePositions;
    public Player player;


    public TapWheelResponse(Map<String, Boolean[][]> availablePositions, Player player, int phase) {
        super(ToolCardType.TAP_WHEEL);
        this.availablePositions = availablePositions;
        this.phase = phase;
        this.player = player;
    }

    public TapWheelResponse(int phase) {
        super(ToolCardType.TAP_WHEEL);
        this.phase = phase;
    }
}

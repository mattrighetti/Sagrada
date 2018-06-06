package ingsw.controller.network.commands;

import ingsw.model.Player;
import ingsw.utilities.ToolCardType;

import java.util.Map;

public class PatternCardToolCardResponse extends UseToolCardResponse {

    public Player player;
    public Map<String,Boolean[][]> availablePositions;

    public PatternCardToolCardResponse(Player player, Map<String, Boolean[][]> availablePositions) {
        super(ToolCardType.PATTERN_CARD);
        this.player = player;
        this.availablePositions = availablePositions;
    }

}

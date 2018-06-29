package ingsw.controller.network.commands;

import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.ToolCardType;

import java.util.Map;

public class LathekinResponse extends UseToolCardResponse {

    public Map<String, Boolean[][]> availablePositions;
    public Player player;
    public boolean secondMove;

    public LathekinResponse(Player player, Map<String, Boolean[][]> availablePositions, boolean secondMove) {
        super(ToolCardType.LATHEKIN);
        this.availablePositions = availablePositions;
        this.player = player;
        this.secondMove = secondMove;
    }
}

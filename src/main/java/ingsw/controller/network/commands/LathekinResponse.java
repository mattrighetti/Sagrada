package ingsw.controller.network.commands;

import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.ToolCardType;

import java.util.Map;

public class LathekinResponse extends UseToolCardResponse {

    public Map<String, Boolean[][]> availablePositions;
    public String playerUsername;
    public PatternCard patternCard;
    public boolean secondMove;

    public LathekinResponse(String playerUsername, PatternCard patternCard, Map<String, Boolean[][]> availablePositions, boolean secondMove) {
        super(ToolCardType.LATHEKIN);
        this.availablePositions = availablePositions;
        this.playerUsername = playerUsername;
        this.patternCard = patternCard;
        this.secondMove = secondMove;
    }
}

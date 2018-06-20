package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.ToolCardType;

import java.util.Map;

public class LathekinResponse extends UseToolCardResponse {

    public Map<String, Boolean[][]> availablePositions;
    public PatternCard patternCard;

    public LathekinResponse(PatternCard patternCard, Map<String, Boolean[][]> availablePositions) {
        super(ToolCardType.LATHEKIN);
        this.availablePositions = availablePositions;
        this.patternCard = patternCard;
    }
}

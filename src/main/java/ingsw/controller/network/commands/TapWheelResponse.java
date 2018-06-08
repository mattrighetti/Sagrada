package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.ToolCardType;

import java.util.Map;

public class TapWheelResponse extends UseToolCardResponse {

    public int phase;
    public Map<String, Boolean[][]> availablePositions;
    public PatternCard patternCard;


    public TapWheelResponse(Map<String, Boolean[][]> availablePositions, PatternCard patternCard, int phase) {
        super(ToolCardType.TAP_WHEEL);
        this.availablePositions = availablePositions;
        this.phase = phase;
        this.patternCard = patternCard;
    }

    public TapWheelResponse(int phase) {
        super(ToolCardType.TAP_WHEEL);
        this.phase = phase;
    }
}

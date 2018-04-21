package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class ShadowThief extends PatternCard {
    public ShadowThief() {
        super("ShadowThief", 4);
        setGrid(GridCreator.fromFile(GridJSONPath.SHADOW_THIEF));
    }
}

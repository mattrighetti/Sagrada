package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class LuxMundi extends PatternCard {
    public LuxMundi() {
        super("LuxMundi", 6);
        setGrid(GridCreator.fromFile(GridJSONPath.LUX_MUNDI));
    }
}

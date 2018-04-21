package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class SunCatcher extends PatternCard {
    public SunCatcher() {
        super("SunCatcher", 4);
        setGrid(GridCreator.fromFile(GridJSONPath.SUN_CATCHER));
    }
}

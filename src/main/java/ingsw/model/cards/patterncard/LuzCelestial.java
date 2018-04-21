package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class LuzCelestial extends PatternCard {
    public LuzCelestial() {
        super("LuzCelestial", 3);
        setGrid(GridCreator.fromFile(GridJSONPath.LUZ_CELESTIAL));
    }
}

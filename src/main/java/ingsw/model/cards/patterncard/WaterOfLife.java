package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class WaterOfLife extends PatternCard {
    public WaterOfLife() {
        super("WaterOfLife", 6);
        setGrid(GridCreator.fromFile(GridJSONPath.WATER_OF_LIFE));
    }
}

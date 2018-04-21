package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class SymphonyOfLight extends PatternCard {
    public SymphonyOfLight() {
        super("SymphonyOfLight", 6);
        setGrid(GridCreator.fromFile(GridJSONPath.SYMPHONY_OF_LIGHT));
    }
}

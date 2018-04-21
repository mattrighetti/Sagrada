package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Batllo extends PatternCard {
    public Batllo() {
        super("Battlo", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.BATTLO));
    }
}

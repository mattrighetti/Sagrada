package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Firelight extends PatternCard {
    public Firelight() {
        super("Firelight", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.FIRELIGHT));
    }
}

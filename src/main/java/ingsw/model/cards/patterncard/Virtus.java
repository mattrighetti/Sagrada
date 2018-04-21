package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Virtus extends PatternCard {
    public Virtus() {
        super("Virtus", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.VIRTUS));
    }
}

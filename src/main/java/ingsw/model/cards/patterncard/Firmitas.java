package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Firmitas extends PatternCard {
    public Firmitas() {
        super("Firmitas", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.FIRMITAS));
    }
}

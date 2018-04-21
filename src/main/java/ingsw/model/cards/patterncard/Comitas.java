package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Comitas extends PatternCard {
    public Comitas() {
        super("Comitas", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.COMITAS));
    }
}

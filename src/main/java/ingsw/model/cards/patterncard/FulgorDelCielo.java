package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class FulgorDelCielo extends PatternCard {
    public FulgorDelCielo() {
        super("FulgorDelCielo", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.FULGOR_DEL_CIELO));
    }
}

package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class AuroraSagradis extends  PatternCard {
    public AuroraSagradis() {
        super("AuroraSagradis", 4);
        setGrid(GridCreator.fromFile(GridJSONPath.AURORA_SAGRADIS));
    }
}

package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class LuxAstram extends PatternCard {
    public LuxAstram() {
        super("LuxAstram", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.LUX_ASTRAM));
    }
}

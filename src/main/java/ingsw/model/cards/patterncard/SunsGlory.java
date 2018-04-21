package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class SunsGlory extends PatternCard {
    public SunsGlory() {
        super("SunsGlory", 6);
        setGrid(GridCreator.fromFile(GridJSONPath.SUNS_GLORY));
    }
}

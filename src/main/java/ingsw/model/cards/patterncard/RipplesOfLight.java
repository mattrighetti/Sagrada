package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class RipplesOfLight extends PatternCard {
    public RipplesOfLight() {
        super("RipplesOfLight", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.RIPPLES_OF_LIGHT));
    }
}

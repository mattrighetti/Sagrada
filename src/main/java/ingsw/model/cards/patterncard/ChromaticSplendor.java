package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class ChromaticSplendor extends PatternCard {
    public ChromaticSplendor() {
        super("ChromaticSplendor", 4);
        setGrid(GridCreator.fromFile(GridJSONPath.CHROMATIC_SPLENDOR));
    }
}

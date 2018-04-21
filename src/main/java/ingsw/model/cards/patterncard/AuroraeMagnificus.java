package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class AuroraeMagnificus extends PatternCard {
    public AuroraeMagnificus() {
        super("AuroraeMagnificus", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.AURORAE_MAGNIFICUS));
   }
}

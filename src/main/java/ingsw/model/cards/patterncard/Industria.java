package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Industria extends PatternCard {
    public Industria() {
        super("Industria", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.INDUSTRIA));
    }
}

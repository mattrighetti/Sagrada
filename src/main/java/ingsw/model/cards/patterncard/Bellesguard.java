package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Bellesguard extends PatternCard {
    public Bellesguard() {
        super("Bellesguard", 3);
        setGrid(GridCreator.fromFile(GridJSONPath.BELLESGUARD));
    }
}

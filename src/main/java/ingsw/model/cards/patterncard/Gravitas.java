package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Gravitas extends PatternCard {
    public Gravitas() {
        super("Gravitas", 5);
        setGrid(GridCreator.fromFile(GridJSONPath.GRAVITAS));
    }
}

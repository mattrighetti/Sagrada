package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class ViaLux extends PatternCard {
    public ViaLux() {
        super("ViaLux", 4);
        setGrid(GridCreator.fromFile(GridJSONPath.VIA_LUX));
    }
}

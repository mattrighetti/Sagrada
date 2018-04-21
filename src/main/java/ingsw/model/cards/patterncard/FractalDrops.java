package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class FractalDrops extends PatternCard {
    public FractalDrops() {
        super("FractalDrops", 3);
        setGrid(GridCreator.fromFile(GridJSONPath.FRACTAL_DROPS));
    }
}

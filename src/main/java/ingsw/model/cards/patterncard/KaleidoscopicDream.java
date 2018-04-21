package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class KaleidoscopicDream extends PatternCard {
    public KaleidoscopicDream() {
        super("KaleidoscopicDream", 4);
        setGrid(GridCreator.fromFile(GridJSONPath.KALEIDOSCOPIC_DREAM));
    }
}

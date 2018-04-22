package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.ArrayList;
import java.util.List;

public class RowShadeVariety extends PublicObjectiveCard {

    public RowShadeVariety() {
        super("RowShadeVariety");
    }

    @Override
    public int check(List<List<Box>> grid) {
        return getPoints() * (int) grid.stream().filter(x ->
                    x.stream().map(y -> y.getDice().getFaceUpValue()).filter(y -> y > 0).distinct().count() == 5 ).count();

    }
}

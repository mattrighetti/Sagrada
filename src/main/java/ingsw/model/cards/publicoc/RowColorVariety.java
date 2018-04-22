package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.ArrayList;
import java.util.List;

public class RowColorVariety extends PublicObjectiveCard {

    public RowColorVariety() {
        super("RowColorVariety");
    }

    //test if distinct() function used on enum works
    @Override
    public int check(List<List<Box>> grid) {
        return getPoints() * (int) grid.stream().filter(x ->
                x.stream().map(y -> y.getDice().getDiceColor()).filter(y -> !y.equals(Color.BLANK)).distinct().count() == 5 ).count();
    }
}

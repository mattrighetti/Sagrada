package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class ColorVariety extends PublicObjectiveCard {

    public ColorVariety() {
        super("ColorVariety", 4);
    }

    @Override
    public int check(List<List<Box>> grid) {
        int minValue = 5;
        for (Color color: Color.values()){
            if(!color.equals(Color.BLANK))
                minValue = Math.min(minValue, countColor(grid, color));
        }
        return minValue;
    }

    private int countColor(List<List<Box>> grid, Color color) {
        return grid.stream().mapToInt(x ->
                (int) x.stream().filter(y -> y.getDice() != null).map( y ->
                        y.getDice().getDiceColor()).filter(y ->
                        y.equals(color)).count()).reduce(0, (sum,x) -> sum + x );
    }
}

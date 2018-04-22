package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public abstract class ShadeCard extends PublicObjectiveCard {

    public ShadeCard(String name, int points) {
        super(name, points);
    }

    public abstract int check(List<List<Box>> grid);

    public int count(List<List<Box>> grid, int valueToCount) {
        return grid.stream().mapToInt(x ->
                (int) x.stream().mapToInt( y ->
                        y.getDice().getFaceUpValue()).filter(y ->
                        y == valueToCount).count()).reduce(0, (sum, x) -> sum + x );
    }
}

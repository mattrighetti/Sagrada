package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public abstract class ShadeCard extends PublicObjectiveCard {

    public ShadeCard(String name, int points) {
        super(name, points);
    }

    public abstract int check(List<List<Box>> grid);

    public int count(List<List<Box>> grid, int valueToCount) {
        return grid.stream()
                .mapToInt(boxes -> (int) boxes.stream()
                        .filter(box -> box.getDice() != null)
                        .mapToInt(box -> box.getDice().getFaceUpValue())
                        .filter(i -> i == valueToCount)
                        .count())
                .reduce(0, (sum, x) -> sum + x);
    }

    @Override
    public int getScore(List<List<Box>> grid) {
        return getPoints() * check(grid);
    }

}

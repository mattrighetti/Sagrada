package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class MediumShades extends PublicObjectiveCard {

    private final int shade1 = 1;
    private final int shade2 = 2;

    public MediumShades() {
        super("MediumShades");
    }

    public int getShade1() {
        return shade1;
    }

    public int getShade2() {
        return shade2;
    }

    @Override
    public int check(List<List<Box>> grid) {
        int noShades1 = 0;
        int noShades2 = 0;
        noShades1 = grid.stream().mapToInt(x ->
                (int) x.stream().mapToInt( y ->
                        y.getDice().getFaceUpValue()).filter(y ->
                        y == getShade1()).count()).reduce(0, (sum,x) -> sum + x );
        noShades2 = grid.stream().mapToInt(x ->
                (int) x.stream().mapToInt( y ->
                        y.getDice().getFaceUpValue()).filter(y ->
                        y == getShade2()).count()).reduce(0, (sum,x) -> sum + x );
        return (int) Math.min(shade1,shade2) * getPoints();

    }
}

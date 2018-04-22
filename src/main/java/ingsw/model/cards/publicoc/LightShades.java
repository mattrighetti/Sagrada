package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class LightShades extends PublicObjectiveCard {

    private final int firstShade = 1;
    private final int secondShade = 2;

    public LightShades() {
        super("LightShades");
    }

    public int getFirstShade() {
        return firstShade;
    }

    public int getSecondShade() {
        return secondShade;
    }

    @Override
    public int check(List<List<Box>> grid) {
        int numOfFirstShades = 0;
        int numOfSecondShades = 0;

        numOfFirstShades = grid.stream().mapToInt(x ->
                (int) x.stream().mapToInt( y ->
                        y.getDice().getFaceUpValue()).filter(y ->
                        y == getFirstShade()).count()).reduce(0, (sum,x) -> sum + x );
        numOfSecondShades = grid.stream().mapToInt(x ->
                (int) x.stream().mapToInt( y ->
                        y.getDice().getFaceUpValue()).filter(y ->
                        y == getSecondShade()).count()).reduce(0, (sum,x) -> sum + x );
        return (int) Math.min(numOfFirstShades,numOfSecondShades) * getPoints();

    }
}

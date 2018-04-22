package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class LightShades extends ShadeCard {
    final int firstShade, secondShade;

    public LightShades() {
        super("LightShades", 2);
        this.firstShade = 1;
        this.secondShade = 2;
    }

    public int getFirstShade() {
        return firstShade;
    }

    public int getSecondShade() {
        return secondShade;
    }

    @Override
    public int check(List<List<Box>> grid) {
        return Math.min(count(grid, getFirstShade()), count(grid, getSecondShade())) * getPoints();
    }

}

package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class MediumShades extends ShadeCard {
    final int firstShade, secondShade;

    public MediumShades() {
        super("MediumShades", 2);
        this.firstShade = 3;
        this.secondShade = 4;
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

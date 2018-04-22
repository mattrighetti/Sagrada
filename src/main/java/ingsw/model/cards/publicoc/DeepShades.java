package ingsw.model.cards.publicoc;

import ingsw.model.cards.patterncard.Box;

import java.util.List;

public class DeepShades extends ShadeCard {
    final int firstShade, secondShade;

    public DeepShades() {
        super("DeepShades", 2);
        this.firstShade = 5;
        this.secondShade = 6;
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

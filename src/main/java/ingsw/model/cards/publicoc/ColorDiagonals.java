package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.HashMap;
import java.util.List;

public class ColorDiagonals extends PublicObjectiveCard {

    private

    public ColorDiagonals() {
        super("ColorDiagonals", 5);
    }

    @Override
    public int check(List<List<Box>> grid) {
        HashMap<int,Color> flags = new HashMap();
        for (int i = 0; i < 5; i++) {
            checkChild(grid, flags, 0, k);
        }
        return flags.size();
    }

    private void checkChild(List<List<Box>> grid, HashMap<int,Color> flags, int row, int column){
        int rowChild = row + 1;
        int columnLeftChild = column - 1;
        int columnRightChild = column + 1;
        boolean isSameColorAsChild = false;



    }
}

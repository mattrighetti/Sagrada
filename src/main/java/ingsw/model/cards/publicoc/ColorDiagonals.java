package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.HashMap;
import java.util.List;

public class ColorDiagonals extends PublicObjectiveCard {

    public ColorDiagonals() {
        super("ColorDiagonals", 5);
    }

    @Override
    public int check(List<List<Box>> grid) {
        HashMap<Integer, Color> flags = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            checkChilds(grid, flags, 0, i, null);
        }
        return flags.size();
    }

    private boolean checkChilds(List<List<Box>> grid, HashMap<Integer, Color> flags, int row, int column, Color parentColor) {

        Color currentColor = grid.get(row).get(column).getDice().getDiceColor();

        int rowChild = row + 1;
        int columnLeftChild = column - 1;
        int columnRightChild = column + 1;

        boolean isSameColorAsChildRight = false;
        boolean isSameColorAsChildLeft = false;

        if (rowChild < 4) {

            if (columnRightChild < 5) {
                if (!flags.containsKey((rowChild * 10) + columnRightChild))
                    isSameColorAsChildRight = checkChilds(grid, flags, rowChild, columnRightChild, currentColor);
                else
                    return flags.get((rowChild * 10) + columnRightChild).equals(currentColor);
            }

            if (columnLeftChild > 0) {
                if (!flags.containsKey((rowChild * 10) + columnLeftChild))
                    isSameColorAsChildLeft = checkChilds(grid, flags, rowChild, columnLeftChild, currentColor);
                else
                    return flags.get((rowChild * 10) + columnLeftChild).equals(currentColor);
            }

            if (parentColor != null && parentColor.equals(currentColor)) {
                flags.put((row * 10) + column, currentColor);
                return true;
            }

            if (isSameColorAsChildRight || isSameColorAsChildLeft) {
                flags.put((row * 10) + column, currentColor);
                return false;
            } else
                return false;

        } else {
            if (parentColor.equals(currentColor)) {
                flags.put((row * 10) + column, currentColor);
                return true;
            }
            return false;
        }
    }
}

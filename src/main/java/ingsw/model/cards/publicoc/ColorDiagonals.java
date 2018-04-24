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

        Color currentColor;

        int rowChild = row + 1;
        int columnLeftChild = column - 1;
        int columnRightChild = column + 1;

        boolean isSameColorAsChildRight = false;
        boolean isSameColorAsChildLeft = false;

        if (parentColor == null)
            parentColor = Color.BLANK;

        if (!(grid.get(row).get(column).getDice() == null))
            currentColor = grid.get(row).get(column).getDice().getDiceColor();
        else
            currentColor = Color.BLANK;

        if (rowChild != 4) {

            if (columnRightChild < 5) {
                if (!flags.containsKey((rowChild * 10) + columnRightChild)) {
                    isSameColorAsChildRight = checkChilds(grid, flags, rowChild, columnRightChild, currentColor);
                } else {
                    if (flags.get((rowChild * 10) + columnRightChild).equals(currentColor)) {
                        flags.put((row * 10) + column, currentColor);
                    }
                }
            }

            if (columnLeftChild > -1) {
                if (!flags.containsKey((rowChild * 10) + columnLeftChild)) {
                    isSameColorAsChildLeft = checkChilds(grid, flags, rowChild, columnLeftChild, currentColor);
                } else {
                    if (flags.get((rowChild * 10) + columnLeftChild).equals(currentColor)) {
                        flags.put((row * 10) + column, currentColor);
                    }
                }
            }

            if ((parentColor.equals(currentColor)) && !currentColor.equals(Color.BLANK)) {
                flags.put((row * 10) + column, currentColor);
                return true;
            } else {
                if ((isSameColorAsChildRight || isSameColorAsChildLeft) && !currentColor.equals(Color.BLANK)) {
                    flags.put((row * 10) + column, currentColor);
                    return true;
                } else
                    return false;
            }

        } else {
            if (parentColor.equals(currentColor) && !parentColor.equals(Color.BLANK)) {
                flags.put((row * 10) + column, currentColor);
                return true;
            }
            return false;
        }
    }

    @Override
    public int getScore(List<List<Box>> grid) {
        return getPoints() * check(grid);
    }

}

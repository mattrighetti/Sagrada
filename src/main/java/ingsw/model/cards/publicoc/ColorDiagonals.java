package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.HashMap;
import java.util.List;

/**
 * Public Objective Card that checks how many diagonals of the same color has the player made in the grid
 */
public class ColorDiagonals extends PublicObjectiveCard {

    /**
     * Create a Color Diagonals instance. Calls <code>super()</code> with parameters the card name "ColorDiagonals" and
     * 5 that represents its points
     */
    public ColorDiagonals() {
        super("ColorDiagonals", 5);
    }

    /**
     * Check how much diagonals are in the grid. Calls <code>checkChild()</code> method for every element of the first
     * ArrayList (<code>grid.get(0)</code>). After that returns the length of the <code>flags</code> HashMap in which
     * every diagonal element is saved.
     * @param grid Grid where the method controls how much dice form a diagonal.
     * @return Return <code>flags.size()</code>, flags contains all the elements that forms a diagonal in the grid.
     */
    @Override
    public int check(List<List<Box>> grid) {
        HashMap<Integer, Color> flags = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            checkChilds(grid, flags, 0, i, null);
        }
        System.out.println(flags.keySet());
        System.out.println(flags.values());
        return flags.size();
    }

    /**
     * Check how much diagonals are in a specific binary tree. In order to achieve the result the algorithm used is
     * recursive and it is nearly the same of a binary tree visit. Starting from a root node (an element in the
     * first line of the ArrayList) of the tree, first the algorithm visits the right sons of the root node,
     * then the left sons. The recursion is used to arrive in the deepest level of the tree (the fourth line) and
     * every time that a son has the same color of the parent node it is added in the HashMap flags.
     * The HashMap is also used to skip useless recursive calls: in fact if an element is in the HashMap and
     * the color of both dice matches you don't need to visit the rest of the tree.After visiting each binary tree
     * (one for every root node) the method returns <code>flags.size()</code>, which is the number of the elements
     * that forms the diagonals in the grid.
     * @param grid The grid you want to check
     * @param flags The Hashmap in which the component of a specific diagonal are added
     * @param row Current grid row
     * @param column Current grid column
     * @param parentColor current color of the parent node
     * @return Boolean in order to do <code>flags.add()</code> with the higher element of a diagonal (parent color will never be the same of the current node)
     */
    private boolean checkChilds(List<List<Box>> grid, HashMap<Integer, Color> flags, int row, int column, Color parentColor) {

        Color currentColor;

        int rowChild = row + 1;
        int columnLeftChild = column - 1;
        int columnRightChild = column + 1;

        boolean isSameColorAsChildRight = false;
        boolean isSameColorAsChildLeft = false;

        //If you are in the first line of the grid, set parent color to blank instead of null

        if (parentColor == null) {
            parentColor = Color.BLANK;
        }

        //If a Dice is not placed in the box set current color to blank, else set it to the current Dice color

        if (grid.get(row).get(column).getDice() != null)
            currentColor = grid.get(row).get(column).getDice().getDiceColor();
        else
            currentColor = Color.BLANK;


        if (rowChild != 4) {

            //Recursive case: visit all the node sons, if they have the same color of the parent add them to the HashMap flags


            //Visit the right son only if it's not in flags

            if (columnRightChild < 5) {
                if (!flags.containsKey((rowChild * 10) + columnRightChild)) {
                    isSameColorAsChildRight = checkChilds(grid, flags, rowChild, columnRightChild, currentColor);
                } else {
                    if (flags.get((rowChild * 10) + columnRightChild).equals(currentColor)) {
                        flags.put((row * 10) + column, currentColor);
                    }
                }
            }

            //Visit the left son only if it's not in flags

            if (columnLeftChild > -1) {
                if (!flags.containsKey((rowChild * 10) + columnLeftChild)) {
                    isSameColorAsChildLeft = checkChilds(grid, flags, rowChild, columnLeftChild, currentColor);
                } else {
                    if (flags.get((rowChild * 10) + columnLeftChild).equals(currentColor)) {
                        flags.put((row * 10) + column, currentColor);
                    }
                }
            }

            if (!currentColor.equals(Color.BLANK)) {

                if ((parentColor.equals(currentColor)) && !currentColor.equals(Color.BLANK)) {
                    flags.put((row * 10) + column, currentColor);
                    return true;
                } else {

                    //This checks if you are in the highest dice of a diagonal: if one of the two sons has returned true it means that it has the same parent color,
                    //which can be added to flags

                    if (isSameColorAsChildRight || isSameColorAsChildLeft) {
                        flags.put((row * 10) + column, currentColor);
                        return true;
                    } else
                        return false;
                }
            } else
                return false;

        } else {


             // Case 0: when you reach the last line of the grid;
             // check if the parent has the same color of the child.

            if (parentColor.equals(currentColor) && !parentColor.equals(Color.BLANK)) {
                flags.put((row * 10) + column, currentColor);
                return true;
            }
            return false;
        }
    }
}

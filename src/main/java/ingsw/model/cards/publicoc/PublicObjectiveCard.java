package ingsw.model.cards.publicoc;

import ingsw.model.cards.Card;
import ingsw.model.cards.patterncard.Box;

import java.util.List;

/**
 * Abstract class of Public Objective Cards
 */
public abstract class PublicObjectiveCard extends Card {

    private int points;

    /**
     * Calls <code>super()</code> with String name as parameter and set int points
     * @param name String Card name
     * @param points int Card points
     */
    public PublicObjectiveCard(String name, int points) {
        super(name);
        this.points = points;
    }

    /**
     * @return PublicObjCard{'NameOfTheCard'}
     */
    @Override
    public String toString() {
        return "PublicObjCard{" +
                "'" + getName() + "'" +
                '}';
    }

    /**
     * Returns the points of the card
     * @return int Card points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Check how many times the goal of the card is achieved
     * @param grid Grid to check
     * @return int How many times goal is achieved
     */
    public abstract int check(List<List<Box>> grid);

    /**
     * Returns the point gained with the Public Objective Card in the grid passed as parameter
     * Launch <code>check(grid)</code> inside the method body
     * @param grid The grid you want to calculate the score of this Public Objective Card
     * @return int The score
     */
    public int getScore(List<List<Box>> grid){ return getPoints()*check(grid); }
}

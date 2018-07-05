package ingsw.utilities;

import java.io.Serializable;

/**
 * Class used to save  dice position in the grid
 */
public class Tuple implements Serializable {
    private int firstInt;
    private int secondInt;

    /**
     * Creates a new Tuple
     * @param firstInt Line index
     * @param secondInt Column index
     */
    public Tuple(int firstInt, int secondInt) {
        this.firstInt = firstInt;
        this.secondInt = secondInt;
    }

    /**
     * Set Line index
     * @param firstInt Line index
     */
    public void setFirst(int firstInt) {
        this.firstInt = firstInt;
    }

    /**
     * Get Line index
     * @return Line index
     */
    public int getFirst() {
        return firstInt;
    }

    /**
     * Get Column index
     * @return Column index
     */
    public int getSecond() {
        return secondInt;
    }

}

package ingsw.utilities;

import java.io.Serializable;

public class Tuple implements Serializable {
    private int firstInt;
    private int secondInt;

    public Tuple(int firstInt, int secondInt) {
        this.firstInt = firstInt;
        this.secondInt = secondInt;
    }

    public void setFirst(int firstInt) {
        this.firstInt = firstInt;
    }

    public void setSecond(int secondInt) {
        this.secondInt = secondInt;
    }

    public int getFirst() {
        return firstInt;
    }

    public int getSecond() {
        return secondInt;
    }

}

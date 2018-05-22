package ingsw.utilities;

import java.io.Serializable;

public class TripleString implements Serializable {
    String firstField;
    String secondField;
    String thirdField;

    public TripleString(String firstField, String secondField, String thirdField) {
        this.firstField = firstField;
        this.secondField = secondField;
        this.thirdField = thirdField;
    }

    public String getFirstField() {
        return firstField;
    }

    public String getSecondField() {
        return secondField;
    }

    public String getThirdField() {
        return thirdField;
    }
}

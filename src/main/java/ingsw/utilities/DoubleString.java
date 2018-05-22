package ingsw.utilities;

import java.io.Serializable;

public class DoubleString implements Serializable {
    String firstField;
    String secondField;

    public DoubleString(String firstField, int secondField) {
        this.firstField = firstField;
        this.secondField = String.valueOf(secondField);
    }

    public String getFirstField() {
        return firstField;
    }

    public String getSecondField() {
        return secondField;
    }
}

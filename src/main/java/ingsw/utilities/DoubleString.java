package ingsw.utilities;

import java.io.Serializable;

public class DoubleString implements Serializable {
    public static final String FIRST_FIELD = "firstField";
    public static final String SECOND_FIELD = "secondField";

    private String firstField;
    private String secondField;

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

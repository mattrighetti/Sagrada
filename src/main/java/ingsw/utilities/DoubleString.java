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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DoubleString) {
            DoubleString obj1 = (DoubleString) obj;
            return obj1.getFirstField().equals(firstField) && obj1.getSecondField().equals(secondField);
        } else return false;
    }
}

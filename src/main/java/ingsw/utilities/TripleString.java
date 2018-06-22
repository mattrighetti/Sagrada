package ingsw.utilities;

import java.io.Serializable;

public class TripleString implements Serializable {
    public static final String FIRST_FIELD = "firstField";
    public static final String SECOND_FIELD = "secondField";
    public static final String THIRD_FIELD = "thirdField";

    private String firstField;
    private String secondField;
    private String thirdField;

    public TripleString(String firstField, String secondField, String thirdField) {
        this.firstField = firstField;
        this.secondField = secondField;
        this.thirdField = thirdField;
    }

    @SuppressWarnings("unused")
    public String getFirstField() {
        return firstField;
    }

    @SuppressWarnings("unused")
    public String getSecondField() {
        return secondField;
    }

    @SuppressWarnings("unused")
    public String getThirdField() {
        return thirdField;
    }
}

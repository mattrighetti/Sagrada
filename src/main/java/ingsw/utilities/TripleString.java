package ingsw.utilities;

import java.io.Serializable;

/**
 * Class used for sending ranking data
 */
public class TripleString implements Serializable {
    public static final String FIRST_FIELD = "firstField";
    public static final String SECOND_FIELD = "secondField";
    public static final String THIRD_FIELD = "thirdField";

    private String firstField;
    private String secondField;
    private String thirdField;

    /**
     * Creates a new TripleString
     *
     * @param firstField  Rank
     * @param secondField Username
     * @param thirdField  Victory
     */
    public TripleString(String firstField, String secondField, String thirdField) {
        this.firstField = firstField;
        this.secondField = secondField;
        this.thirdField = thirdField;
    }

    /**
     * Get Rank
     *
     * @return Rank
     */
    @SuppressWarnings("unused")
    public String getFirstField() {
        return firstField;
    }

    /**
     * Get username
     *
     * @return Username
     */
    @SuppressWarnings("unused")
    public String getSecondField() {
        return secondField;

    }

    /**
     * Get number of victory
     *
     * @return Victories
     */
    @SuppressWarnings("unused")
    public String getThirdField() {
        return thirdField;
    }
}

package ingsw.utilities;

import java.io.Serializable;

/**
 * Class used to updated the Available matches containing
 * the match name and the number of connected
 * user with that controller.
 */
public class DoubleString implements Serializable {
    public static final String FIRST_FIELD = "firstField";
    public static final String SECOND_FIELD = "secondField";

    private String firstField;
    private String secondField;

    /**
     * Creates a new DoubleString
     * @param firstField Match name
     * @param secondField connected users
     */
    public DoubleString(String firstField, int secondField) {
        this.firstField = firstField;
        this.secondField = String.valueOf(secondField);
    }

    /**
     * Get the first field
     * @return Match name
     */
    public String getFirstField() {
        return firstField;
    }

    /**
     * Get the second field
     * @return connected users
     */
    public String getSecondField() {
        return secondField;
    }

    /**
     * Check if two MoveStatus has the same values.
     * @param obj MoveStatus to verify
     * @return true if they have the same fields
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DoubleString) {
            DoubleString obj1 = (DoubleString) obj;
            return obj1.getFirstField().equals(firstField) && obj1.getSecondField().equals(secondField);
        } else return false;
    }
}

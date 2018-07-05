package ingsw.model;

/**
 * Enumeration with the six colors:
 * - Red
 * - Green
 * - Purple
 * - Yellow
 * - Green
 * - Blank
 */
public enum Color {
    RED,
    GREEN,
    PURPLE,
    BLUE,
    YELLOW,
    BLANK;

    /**
     * Returns the colot with the first character uppercase
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(name().charAt(0) + name().substring(1).toLowerCase());
    }
}

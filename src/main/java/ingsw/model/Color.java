package ingsw.model;

public enum Color {
    RED,
    GREEN,
    PURPLE,
    BLUE,
    YELLOW,
    BLANK;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}

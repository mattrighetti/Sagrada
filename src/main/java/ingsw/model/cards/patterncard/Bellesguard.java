package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class Bellesguard extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"YELLOW\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"GREEN\" }\n" +
            "    ]\n" +
            "]";

    /**
     * Creates a new Bellesguard pattern card
     */
    public Bellesguard() {
        super("Bellesguard", 3);
        setGrid(GridCreator.fromString(json));
    }
}

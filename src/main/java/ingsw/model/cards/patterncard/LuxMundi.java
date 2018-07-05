package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class LuxMundi extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"value\": 2 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"GREEN\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ]\n" +
            "]";

    /**
     * Creates a new LuxMundi pattern card
     */
    public LuxMundi() {
        super("LuxMundi", 6);
        setGrid(GridCreator.fromString(json));
    }
}

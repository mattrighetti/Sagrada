package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class Virtus extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"GREEN\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 2 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ]\n" +
            "]";

    /**
     * Creates a new Virtus pattern card
     */
    public Virtus() {
        super("Virtus", 5);
        setGrid(GridCreator.fromString(json));
    }
}

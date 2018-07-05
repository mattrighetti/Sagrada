package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class Batllo extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 2 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 1 },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"value\": 3 }\n" +
            "    ]\n" +
            "]";

    /**
     * Creates a new Batllo pattern card
     */
    public Batllo() {
        super("Batllo", 5);
        setGrid(GridCreator.fromString(json));
    }
}

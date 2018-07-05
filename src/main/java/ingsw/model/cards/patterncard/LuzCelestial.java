package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class LuzCelestial extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 3 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ]\n" +
            "]";

    /**
     * Creates a new LuzCelestial pattern card
     */
    public LuzCelestial() {
        super("LuzCelestial", 3);
        setGrid(GridCreator.fromString(json));
    }
}

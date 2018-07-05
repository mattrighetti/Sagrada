package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class ShadowThief extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"value\": 3 }\n" +
            "    ]\n" +
            "]";

    /**
     * Creates a new ShadowThief pattern card
     */
    public ShadowThief() {
        super("ShadowThief", 4);
        setGrid(GridCreator.fromString(json));
    }
}

package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class KaleidoscopicDream extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 1 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 4 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"GREEN\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"YELLOW\" }\n" +
            "    ]\n" +
            "  ]";

    /**
     * Creates a new KaleidoscopicDream pattern card
     */
    public KaleidoscopicDream() {
        super("KaleidoscopicDream", 4);
        setGrid(GridCreator.fromString(json));
    }
}

package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class LuxAstram extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 4 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"GREEN\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"PURPLE\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ]\n" +
            "]";

    public LuxAstram() {
        super("LuxAstram", 5);
        setGrid(GridCreator.fromString(json));
    }
}

package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class ChromaticSplendor extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"value\": 1 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 4 }\n" +
            "    ]\n" +
            "]";

    public ChromaticSplendor() {
        super("ChromaticSplendor", 4);
        setGrid(GridCreator.fromString(json));
    }
}

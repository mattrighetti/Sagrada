package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class SymphonyOfLight extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 1 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"RED\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ]\n" +
            "]";

    public SymphonyOfLight() {
        super("SymphonyOfLight", 6);
        setGrid(GridCreator.fromString(json));
    }
}

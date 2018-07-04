package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class ViaLux extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 2 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"RED\" }\n" +
            "    ]\n" +
            "]";

    public ViaLux() {
        super("ViaLux", 4);
        setGrid(GridCreator.fromString(json));
    }
}

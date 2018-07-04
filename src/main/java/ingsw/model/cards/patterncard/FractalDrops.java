package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class FractalDrops extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"value\": 6 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 1 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ]\n" +
            "]";

    public FractalDrops() {
        super("FractalDrops", 3);
        setGrid(GridCreator.fromString(json));
    }
}

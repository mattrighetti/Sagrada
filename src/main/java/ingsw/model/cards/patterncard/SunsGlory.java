package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class SunsGlory extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 4 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"value\": 3 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"value\": 1 }\n" +
            "    ]\n" +
            "]";

    public SunsGlory() {
        super("SunsGlory", 6);
        setGrid(GridCreator.fromString(json));
    }
}

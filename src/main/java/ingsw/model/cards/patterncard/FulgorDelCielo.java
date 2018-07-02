package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class FulgorDelCielo extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLUE\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 5 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ]\n" +
            "]";

    public FulgorDelCielo() {
        super("FulgorDelCielo", 5);
        setGrid(GridCreator.fromString(json));
    }
}

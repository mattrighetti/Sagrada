package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Firelight extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"value\": 3 },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"YELLOW\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"RED\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 6 }\n" +
            "    ]\n" +
            "]";

    public Firelight() {
        super("Firelight", 5);
        setGrid(GridCreator.fromString(json));
    }
}

package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Industria extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 5 },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 1 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"RED\" }\n" +
            "    ]\n" +
            "]";

    public Industria() {
        super("Industria", 5);
        setGrid(GridCreator.fromString(json));
    }
}

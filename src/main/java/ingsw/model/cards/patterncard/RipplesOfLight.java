package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class RipplesOfLight extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 5 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"BLUE\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"value\": 6 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"RED\" }\n" +
            "    ]\n" +
            "]";

    public RipplesOfLight() {
        super("RipplesOfLight", 5);
        setGrid(GridCreator.fromString(json));
    }
}

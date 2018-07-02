package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class AuroraeMagnificus extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 2 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"YELLOW\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"PURPLE\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 4 }\n" +
            "    ]\n" +
            "]";

    public AuroraeMagnificus() {
        super("AuroraeMagnificus", 5);
        setGrid(GridCreator.fromString(json));
    }
}

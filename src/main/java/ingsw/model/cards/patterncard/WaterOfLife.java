package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class WaterOfLife extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 1 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"PURPLE\" }\n" +
            "    ]\n" +
            "]";

    public WaterOfLife() {
        super("WaterOfLife", 6);
        setGrid(GridCreator.fromString(json));
    }
}

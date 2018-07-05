package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class AuroraSagradis extends  PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"RED\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLUE\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"YELLOW\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"GREEN\" },\n" +
            "      { \"value\": 2 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ]\n" +
            "]";

    /**
     * Creates a new AuroraSagradis pattern card
     */
    public AuroraSagradis() {
        super("AuroraSagradis", 4);
        setGrid(GridCreator.fromString(json));
    }
}

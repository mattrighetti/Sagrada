package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuroraeMagnificusTest {
    AuroraeMagnificus auroraeMagnificus;
    String expectedGridJSON;
    Box[][] expectedGrid = new Box[4][5];

    @BeforeEach
    void setUp() { //TODO rifare
        auroraeMagnificus = new AuroraeMagnificus();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 2 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"YELLOW\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 6 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"PURPLE\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 1 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"value\": 4 }\n" +
                "    ]\n" +
                "]";
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, Box[][].class);
    }

    @Test
    void toStringTest() {
        assertEquals("AuroraeMagnificus", auroraeMagnificus.getName());
        assertEquals("PatternCard{'AuroraeMagnificus'}", auroraeMagnificus.toString());
    }
}
package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuroraeMagnificusTest {
    AuroraeMagnificus auroraeMagnificus;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

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
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("AuroraeMagnificus", auroraeMagnificus.getName());
        assertEquals("PatternCard{'AuroraeMagnificus'}", auroraeMagnificus.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), auroraeMagnificus.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), auroraeMagnificus.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
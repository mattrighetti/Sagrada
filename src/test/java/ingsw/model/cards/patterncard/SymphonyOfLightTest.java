package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SymphonyOfLightTest {
    SymphonyOfLight symphonyOfLight;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        symphonyOfLight = new SymphonyOfLight();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"value\": 2 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 1 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"value\": 6 },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"RED\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"value\": 0 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 3 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"value\": 0 }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("SymphonyOfLight", symphonyOfLight.getName());
        assertEquals("PatternCard{'SymphonyOfLight'}", symphonyOfLight.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), symphonyOfLight.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), symphonyOfLight.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
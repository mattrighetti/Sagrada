package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LuzCelestialTest {
    LuzCelestial luzCelestial;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;
    
    @BeforeEach
    void setUp(){
        luzCelestial = new LuzCelestial();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"value\": 0 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"value\": 3 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 6 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 0 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("LuzCelestial", luzCelestial.getName());
        assertEquals("PatternCard{'LuzCelestial'}", luzCelestial.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), luzCelestial.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), luzCelestial.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
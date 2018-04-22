package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.model.Color;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RipplesOfLightTest {
    RipplesOfLight ripplesOfLight;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;
    
    @BeforeEach
    void setUp(){
        ripplesOfLight = new RipplesOfLight();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"value\": 5 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"color\": \"BLUE\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
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
                "]" ;
                expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("RipplesOfLight", ripplesOfLight.getName());
        assertEquals("PatternCard{'RipplesOfLight'}", ripplesOfLight.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), ripplesOfLight.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), ripplesOfLight.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
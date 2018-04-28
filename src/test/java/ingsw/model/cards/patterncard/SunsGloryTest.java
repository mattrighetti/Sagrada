package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SunsGloryTest {
    SunsGlory sunsGlory;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        sunsGlory = new SunsGlory();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"value\": 1 },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 4 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 6 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"value\": 3 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"value\": 1 }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("SunsGlory", sunsGlory.getName());
        assertEquals("PatternCard{'SunsGlory'}", sunsGlory.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), sunsGlory.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), sunsGlory.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
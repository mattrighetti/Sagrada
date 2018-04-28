package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SunCatcherTest {
    SunCatcher sunCatcher;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        sunCatcher = new SunCatcher();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"YELLOW\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"value\": 3 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"PURPLE\" }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("SunCatcher", sunCatcher.getName());
        assertEquals("PatternCard{'SunCatcher'}", sunCatcher.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), sunCatcher.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), sunCatcher.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
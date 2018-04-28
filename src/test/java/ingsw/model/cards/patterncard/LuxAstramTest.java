package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LuxAstramTest {
    LuxAstram luxAstram;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        luxAstram = new LuxAstram();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 1 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 4 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 6 },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"GREEN\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 1 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"value\": 3 },\n" +
                "      { \"color\": \"PURPLE\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid =  (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("LuxAstram", luxAstram.getName());
        assertEquals("PatternCard{'LuxAstram'}", luxAstram.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), luxAstram.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), luxAstram.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
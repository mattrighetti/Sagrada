package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VirtusTest {
    Virtus virtus;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        virtus = new Virtus();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"value\": 4 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"GREEN\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 6 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"value\": 2 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 3 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"value\": 1 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("Virtus", virtus.getName());
        assertEquals("PatternCard{'Virtus'}", virtus.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), virtus.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), virtus.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
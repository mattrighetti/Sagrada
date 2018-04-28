package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirelightTest {
    Firelight firelight;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        firelight = new Firelight();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"value\": 3 },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"value\": 1 },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 6 },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"YELLOW\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"RED\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"value\": 6 }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("Firelight", firelight.getName());
        assertEquals("PatternCard{'Firelight'}", firelight.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), firelight.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), firelight.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
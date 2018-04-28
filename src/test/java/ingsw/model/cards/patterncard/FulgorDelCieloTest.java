package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FulgorDelCieloTest {
    FulgorDelCielo fulgorDelCielo;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        fulgorDelCielo = new FulgorDelCielo();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLUE\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"value\": 5 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 6 },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"value\": 3 },\n" +
                "      { \"value\": 1 },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("FulgorDelCielo", fulgorDelCielo.getName());
        assertEquals("PatternCard{'FulgorDelCielo'}", fulgorDelCielo.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), fulgorDelCielo.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), fulgorDelCielo.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
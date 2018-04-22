package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GravitasTest {
    Gravitas gravitas;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        gravitas = new Gravitas();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"value\": 1 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 3 },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 0 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 6 },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"value\": 0 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 1 }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("Gravitas", gravitas.getName());
        assertEquals("PatternCard{'Gravitas'}", gravitas.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), gravitas.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), gravitas.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
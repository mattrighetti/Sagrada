package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirmitasTest {
    Firmitas firmitas;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        firmitas = new Firmitas();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 6 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 3 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 3 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 1 },\n" +
                "      { \"value\": 0 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 1 },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 4 }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("Firmitas", firmitas.getName());
        assertEquals("PatternCard{'Firmitas'}", firmitas.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), firmitas.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), firmitas.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
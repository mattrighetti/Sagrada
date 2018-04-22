package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComitasTest {
    Comitas comitas;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;


    @BeforeEach
    void setUp(){
        comitas = new Comitas();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 6 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"YELLOW\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"value\": 0 },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"value\": 5 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 1 },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"value\": 3 },\n" +
                "      { \"value\": 0 }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("Comitas", comitas.getName());
        assertEquals("PatternCard{'Comitas'}", comitas.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), comitas.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), comitas.getGrid().get(i).get(j).getValue());
            }
        }
    }

}
package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FractalDropsTest {
    FractalDrops fractalDrops;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        fractalDrops = new FractalDrops();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"value\": 6 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 1 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("FractalDrops", fractalDrops.getName());
        assertEquals("PatternCard{'FractalDrops'}", fractalDrops.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), fractalDrops.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), fractalDrops.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
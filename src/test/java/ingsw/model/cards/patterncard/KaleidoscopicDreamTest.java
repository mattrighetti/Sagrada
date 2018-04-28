package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KaleidoscopicDreamTest {
    KaleidoscopicDream kaleidoscopicDream;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        kaleidoscopicDream = new KaleidoscopicDream();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 1 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 4 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 3 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"GREEN\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"color\": \"YELLOW\" }\n" +
                "    ]\n" +
                "  ]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("KaleidoscopicDream", kaleidoscopicDream.getName());
        assertEquals("PatternCard{'KaleidoscopicDream'}", kaleidoscopicDream.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), kaleidoscopicDream.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), kaleidoscopicDream.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
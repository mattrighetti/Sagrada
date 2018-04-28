package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BatlloTest {
    Batllo batllo;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp() {
        batllo = new Batllo();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 6 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 3 },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"value\": 2 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 1 },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"value\": 3 }\n" +
                "    ]\n" +
                "]";
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("Battlo", batllo.getName());
        assertEquals("PatternCard{'Battlo'}", batllo.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), batllo.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), batllo.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
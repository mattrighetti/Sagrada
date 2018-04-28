package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShadowThiefTest {
    ShadowThief shadowThief;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;

    @BeforeEach
    void setUp(){
        shadowThief = new ShadowThief();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"value\": 6 },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 5 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"value\": 6 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"value\": 4 },\n" +
                "      { \"value\": 3 }\n" +
                "    ]\n" +
                "]" ;
        expectedGrid = (new Gson()).fromJson(expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("ShadowThief", shadowThief.getName());
        assertEquals("PatternCard{'ShadowThief'}", shadowThief.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), shadowThief.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), shadowThief.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
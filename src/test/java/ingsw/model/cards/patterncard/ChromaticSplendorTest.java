package ingsw.model.cards.patterncard;

import com.google.gson.Gson;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChromaticSplendorTest {
    ChromaticSplendor chromaticSplendor;
    String expectedGridJSON;
    List<List<Box>> expectedGrid;


    @BeforeEach
    void setUp(){
        chromaticSplendor = new ChromaticSplendor();
        expectedGridJSON = "[\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"GREEN\" },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 2 },\n" +
                "      { \"color\": \"YELLOW\" },\n" +
                "      { \"value\": 5 },\n" +
                "      { \"color\": \"BLUE\" },\n" +
                "      { \"value\": 1 }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"color\": \"RED\" },\n" +
                "      { \"value\": 3 },\n" +
                "      { \"color\": \"PURPLE\" },\n" +
                "      { \"color\": \"BLANK\" }\n" +
                "    ],\n" +
                "    [\n" +
                "      { \"value\": 1 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 6 },\n" +
                "      { \"color\": \"BLANK\" },\n" +
                "      { \"value\": 4 }\n" +
                "    ]\n" +
                "]";
        expectedGrid = (new Gson()).fromJson( expectedGridJSON, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("ChromaticSplendor", chromaticSplendor.getName());
        assertEquals("PatternCard{'ChromaticSplendor'}", chromaticSplendor.toString());
    }

    @Test
    void testGrid(){
        for( int i = 0; i < 4; i++){
            for( int j = 0; j < 5; j++){
                assertEquals(expectedGrid.get(i).get(j).getColor(), chromaticSplendor.getGrid().get(i).get(j).getColor());
                assertEquals(expectedGrid.get(i).get(j).getValue(), chromaticSplendor.getGrid().get(i).get(j).getValue());
            }
        }
    }
}
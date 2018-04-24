package ingsw.model.cards.publicoc;

import com.google.gson.Gson;
import ingsw.model.cards.patterncard.Box;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RowShadeVarietyTest {
    private RowShadeVariety rowShadeVariety;
    private List<List<Box>> grid;

    @BeforeEach

    void setUp() {
        rowShadeVariety = new RowShadeVariety();
        String gridJson = "[[{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":4,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":3,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":2,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":1,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":6,\"diceColor\":\"BLUE\"}}],[{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":4,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\"}],[{\"color\":\"BLANK\"},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":4,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":4,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"}],[{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"}]]\n";
        grid = new Gson().fromJson(gridJson, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("RowShadeVariety", rowShadeVariety.getName());
        assertEquals("PublicObjCard{'RowShadeVariety'}", rowShadeVariety.toString());
    }

    @Test
    void checkTest() {
        assertEquals(1, rowShadeVariety.check(grid));
    }

    @Test
    void getScoreTest() {
        assertEquals(5, rowShadeVariety.getScore(grid));
    }
}
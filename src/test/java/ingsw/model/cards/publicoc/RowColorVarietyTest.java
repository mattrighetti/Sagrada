package ingsw.model.cards.publicoc;

import com.google.gson.Gson;
import ingsw.model.cards.patterncard.Box;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RowColorVarietyTest {
    RowColorVariety rowColorVariety;
    List<List<Box>> grid;

    @BeforeEach
    void setUp() {
        rowColorVariety = new RowColorVariety();
        String gridJson = "[[{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":4,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":3,\"diceColor\":\"YELLOW\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":2,\"diceColor\":\"PURPLE\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":1,\"diceColor\":\"RED\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":6,\"diceColor\":\"GREEN\"}}],[{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":4,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\"}],[{\"color\":\"BLANK\"},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":4,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\",\"dice\":{\"faceUpValue\":4,\"diceColor\":\"BLUE\"}},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"}],[{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"},{\"color\":\"BLANK\"}]]\n";
        grid = new Gson().fromJson(gridJson, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("RowColorVariety", rowColorVariety.getName());
        assertEquals("PublicObjCard{'RowColorVariety'}", rowColorVariety.toString());
    }

    @Test
    void checkTest() {
        assertEquals(1, rowColorVariety.check(grid));
    }

    @Test
    void getScoreTest() {
        assertEquals(6, rowColorVariety.getScore(grid));
    }
}
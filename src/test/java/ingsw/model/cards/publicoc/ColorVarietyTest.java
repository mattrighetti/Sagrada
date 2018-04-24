package ingsw.model.cards.publicoc;

import com.google.gson.Gson;
import ingsw.model.cards.patterncard.Box;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorVarietyTest {
    ColorVariety colorVariety;
    List<List<Box>> grid;

    @BeforeEach
    void setUp() {
        colorVariety = new ColorVariety();
        String jsonGrid = "[\n" +
                "    [{\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 0,\n" +
                "            \"diceColor\": \"BLUE\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }],\n" +
                "    [{\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 0,\n" +
                "            \"diceColor\": \"PURPLE\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 0,\n" +
                "            \"diceColor\": \"BLUE\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }],\n" +
                "    [{\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 0,\n" +
                "            \"diceColor\": \"RED\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 0,\n" +
                "            \"diceColor\": \"BLUE\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }],\n" +
                "    [{\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 0,\n" +
                "            \"diceColor\": \"YELLOW\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 0,\n" +
                "            \"diceColor\": \"GREEN\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }]\n" +
                "]\n";
        grid = new Gson().fromJson(jsonGrid, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("ColorVariety", colorVariety.getName());
        assertEquals("PublicObjCard{'ColorVariety'}", colorVariety.toString());
    }

    @Test
    void checkTest() {
        assertEquals(1, colorVariety.check(grid));
    }

    @Test
    void getScoreTest() {
        assertEquals(4, colorVariety.getScore(grid));
    }
}
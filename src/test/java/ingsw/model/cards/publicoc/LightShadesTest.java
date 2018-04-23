package ingsw.model.cards.publicoc;

import com.google.gson.Gson;
import ingsw.model.cards.patterncard.Box;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LightShadesTest {
    LightShades lightShades;
    List<List<Box>> grid;

    @BeforeEach
    void setUp() {
        lightShades = new LightShades();
        String gridJson = "[\n" +
                "    [{\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 1,\n" +
                "            \"diceColor\": \"BLUE\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 1,\n" +
                "            \"diceColor\": \"BLUE\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 2,\n" +
                "            \"diceColor\": \"BLUE\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }],\n" +
                "    [{\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 2,\n" +
                "            \"diceColor\": \"BLUE\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 1,\n" +
                "            \"diceColor\": \"GREEN\"\n" +
                "        }\n" +
                "    }],\n" +
                "    [{\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 2,\n" +
                "            \"diceColor\": \"GREEN\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }],\n" +
                "    [{\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 3,\n" +
                "            \"diceColor\": \"GREEN\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }]\n" +
                "]";
        grid = new Gson().fromJson(gridJson, GridCreator.GRID_TYPE);
    }

    @Test
    void toStringTest() {
        assertEquals("LightShades", lightShades.getName());
        assertEquals("PublicObjCard{'LightShades'}", lightShades.toString());
    }

    @Test
    void checkTest() {
        assertEquals(3, lightShades.check(grid));
    }

    @Test
    void getScoreTest() {
        assertEquals(6, lightShades.getScore(grid));
    }
}
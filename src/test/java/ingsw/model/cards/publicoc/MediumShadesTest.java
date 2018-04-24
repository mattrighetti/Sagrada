package ingsw.model.cards.publicoc;

import com.google.gson.Gson;
import ingsw.model.cards.patterncard.Box;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MediumShadesTest {
    MediumShades mediumShades;
    List<List<Box>> grid;

    @BeforeEach
    void setUp() {
        mediumShades = new MediumShades();
        String gridJson = "[\n" +
                "    [{\n" +
                "        \"color\": \"BLANK\",\n" +
                "        \"dice\": {\n" +
                "            \"faceUpValue\": 4,\n" +
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
                "            \"faceUpValue\": 3,\n" +
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
                "            \"faceUpValue\": 4,\n" +
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
        assertEquals("MediumShades", mediumShades.getName());
        assertEquals("PublicObjCard{'MediumShades'}", mediumShades.toString());
    }

    @Test
    void checkTest() {
        assertEquals(2, mediumShades.check(grid));
    }

    @Test
    void getScoreTest() {
        assertEquals(4, mediumShades.getScore(grid));
    }

}
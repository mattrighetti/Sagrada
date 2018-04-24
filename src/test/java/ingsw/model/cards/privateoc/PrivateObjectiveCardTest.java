package ingsw.model.cards.privateoc;

import com.google.gson.Gson;
import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.publicoc.ColumnColorVariety;
import ingsw.utilities.GridCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrivateObjectiveCardTest {

    PrivateObjectiveCard privateObjectiveCard;
    List<List<Box>> grid;

    @BeforeEach
    void setUp() {
        privateObjectiveCard = new PrivateObjectiveCard(Color.BLUE);
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
                "            \"diceColor\": \"BLUE\"\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"color\": \"BLANK\"\n" +
                "    }]\n" +
                "]\n";
        grid = new Gson().fromJson(jsonGrid, GridCreator.GRID_TYPE);
    }

    @Test
    void getColorTest() {
        assertEquals(Color.BLUE, privateObjectiveCard.getColor());
    }

    @Test
    void toStringTest() {
        assertEquals("PrivateObjCard{'Blue'}", privateObjectiveCard.toString());
    }

    @Test
    void checkTest() {
        assertEquals(4, privateObjectiveCard.check(grid));
    }


}
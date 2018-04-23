package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShadeVarietyTest {
    ShadeVariety shadeVariety;

    @BeforeEach
    void setUp() {
        shadeVariety = new ShadeVariety();
    }

    @Test
    void toStringTest() {
        assertEquals("ShadeVariety", shadeVariety.getName());
        assertEquals("PublicObjCard{'ShadeVariety'}", shadeVariety.toString());
    }

    @Test
    void checkTest() {
        //shadeVariety.check();
        //TODO test
    }

}
package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RipplesOfLightTest {
    RipplesOfLight ripplesOfLight;

    @BeforeEach
    void setUp(){
        ripplesOfLight = new RipplesOfLight();
    }

    @Test
    void toStringTest() {
        assertEquals("RipplesOfLight", ripplesOfLight.getName());
        assertEquals("PatternCard{'RipplesOfLight'}", ripplesOfLight.toString());
    }
}
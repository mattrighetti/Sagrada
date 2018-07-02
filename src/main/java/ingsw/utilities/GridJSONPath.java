package ingsw.utilities;

import java.io.IOException;
import java.io.InputStream;

/**
 * Enumeration that stores every path of every possible grid defined in the Sagrada game
 */
public enum GridJSONPath {
    AURORAE_MAGNIFICUS("AuroraeMagnificus.json"),
    AURORA_SAGRADIS("AuroraSagradis.json"),
    BATLLO("Batllo.json"),
    BELLESGUARD("Bellesguard.json"),
    CHROMATIC_SPLENDOR("ChromaticSplendor.json"),
    COMITAS("Comitas.json"),
    FIRELIGHT("Firelight.json"),
    FIRMITAS("Firmitas.json"),
    FRACTAL_DROPS("FractalDrops.json"),
    FULGOR_DEL_CIELO("FulgorDelCielo.json"),
    GRAVITAS("Gravitas.json"),
    INDUSTRIA("Industria.json"),
    KALEIDOSCOPIC_DREAM("KaleidoscopicDream.json"),
    LUX_ASTRAM("LuxAstram.json"),
    LUX_MUNDI("LuxMundi.json"),
    LUZ_CELESTIAL("LuzCelestial.json"),
    RIPPLES_OF_LIGHT("RipplesOfLight.json"),
    SHADOW_THIEF("ShadowThief.json"),
    SUN_CATCHER("SunCatcher.json"),
    SUNS_GLORY("SunsGlory.json"),
    SYMPHONY_OF_LIGHT("SymphonyOfLight.json"),
    VIA_LUX("ViaLux.json"),
    VIRTUS("Virtus.json"),
    WATER_OF_LIFE("WaterOfLife.json");

    private InputStream filePath;

    /**
     * Constructor that creates the JSON grid's file path String
     * @param fileName JSON file's name that defines the PatternCard
     */
    GridJSONPath(String fileName) {
        filePath = getClass().getResourceAsStream("/patterncards-json/" + fileName);
        filePath.mark(0);
    }

    public InputStream getFilePath() {
        try {
            filePath.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    @Override
    public String toString() {
        return filePath.toString();
    }

}

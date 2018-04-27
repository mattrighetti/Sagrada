package ingsw.utilities;

/**
 * Enumeration that stores every path of every possible grid defined in the Sagrada game
 */
public enum GridJSONPath {
    AURORAE_MAGNIFICUS("AuroraeMagnificus.json"),
    AURORA_SAGRADIS("AuroraSagradis.json"),
    BATTLO("Battlo.json"),
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

    private String filePath;

    /**
     * Constructor that creates the JSON grid's file path String
     * @param fileName
     */
    GridJSONPath(String fileName) {
        filePath = "src/main/resources/patterncards-json/" + fileName;
    }

    @Override
    public String toString() {
        return filePath;
    }

}

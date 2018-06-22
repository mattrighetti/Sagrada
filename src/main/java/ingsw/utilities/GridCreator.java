package ingsw.utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ingsw.model.Player;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.patterncard.PatternCard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * GridCreator is a class that uses the Gson class to read and create grids for pattern cards from JSON files.
 */
public final class GridCreator {
    private static Gson gson = new Gson();
    private static JsonReader jsonReader;
    public static final Type GRID_TYPE = new TypeToken<ArrayList<ArrayList<Box>>>(){}.getType();

    private GridCreator() {
    }

    /**
     * Reads a pattern card's grid from JSON file passed as parameter
     * @param path path of the JSON file of the PatternCard
     * @return PatternCard grid
     */
    public static List<List<Box>> fromFile(GridJSONPath path) {
        try {
            jsonReader = new JsonReader(new FileReader(path.toString()));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find file");
        }
        return gson.fromJson(jsonReader, GRID_TYPE);
    }

    @SuppressWarnings("unused")
    public static PatternCard fromString(String string, PatternCard patternCard) {
        return gson.fromJson(string, patternCard.getClass());
    }

    @SuppressWarnings("unused")
    public static String serializePatternCard(Player player) {
        return gson.toJson(player.getPatternCard());
    }
}

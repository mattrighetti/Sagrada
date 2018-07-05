package ingsw.utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ingsw.model.cards.patterncard.Box;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * GridCreator is a class that uses the Gson class to read and create grids for pattern cards from JSON files.
 */
public final class GridCreator {
    private static Gson gson = new Gson();
    public static final Type GRID_TYPE = new TypeToken<ArrayList<ArrayList<Box>>>() {
    }.getType();

    private GridCreator() {
    }

    /**
     * Fills the pattern cards grid redaing from JSON
     * @param jsonString
     * @return
     */
    public static List<List<Box>> fromString(String jsonString) {
        return gson.fromJson(jsonString, GRID_TYPE);
    }
}

package ingsw.utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ingsw.model.cards.patterncard.Box;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * GridCreator is a class that uses the Gson class to read and create grids for pattern cards from JSON files.
 */
public final class GridCreator {
    static Gson gson;
    static JsonReader jsonReader;
    public static Type GRID_TYPE = new TypeToken<ArrayList<ArrayList<Box>>>(){}.getType();


    private GridCreator() {}

    /**
     * Reads a patter card's grid from JSON file passed as parameter
     * @param path
     * @return
     */
    public static List<List<Box>> fromFile(GridJSONPath path) {
        gson = new Gson();
        try {
            jsonReader = new JsonReader(new FileReader(path.toString()));
        } catch (FileNotFoundException e) {
            System.err.println("It's ok");
        }
        return gson.fromJson(jsonReader, GRID_TYPE);
    }
}

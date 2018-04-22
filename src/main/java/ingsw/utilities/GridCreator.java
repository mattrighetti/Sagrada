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

public final class GridCreator {
    static Gson gson;
    static JsonReader jsonReader;

    private GridCreator() {}

    public static List<List<Box>> fromFile(GridJSONPath path) {
        gson = new Gson();
        try {
            jsonReader = new JsonReader(new FileReader(path.toString()));
        } catch (FileNotFoundException e) {
            System.err.println("It's ok");
        }
        Type typeOfGrid = new TypeToken<ArrayList<ArrayList<Box>>>(){}.getType();
        return gson.fromJson(jsonReader, typeOfGrid);
    }
}

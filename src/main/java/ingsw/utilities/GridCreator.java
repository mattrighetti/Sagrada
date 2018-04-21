package ingsw.utilities;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import ingsw.model.cards.patterncard.Box;

import java.io.FileNotFoundException;
import java.io.FileReader;

public final class GridCreator {
    static Gson gson;
    static JsonReader jsonReader;

    private GridCreator() {}

    public static Box[][] fromFile(GridJSONPath path) {
        gson = new Gson();
        try {
            jsonReader = new JsonReader(new FileReader(path.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(jsonReader, Box[][].class);
    }
}

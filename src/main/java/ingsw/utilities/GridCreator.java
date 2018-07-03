package ingsw.utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ingsw.model.Player;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.patterncard.PatternCard;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * GridCreator is a class that uses the Gson class to read and create grids for pattern cards from JSON files.
 */
public final class GridCreator {
    private static Gson gson = new Gson();
    private static JsonReader jsonReader;
    public static final Type GRID_TYPE = new TypeToken<ArrayList<ArrayList<Box>>>() {
    }.getType();

    private GridCreator() {
    }

    public static List<List<Box>> fromString(String jsonString) {
        return gson.fromJson(jsonString, GRID_TYPE);
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

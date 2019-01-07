package capstone.my.annin.londontubeschedule.utils;

import capstone.my.annin.londontubeschedule.model.Lines;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = JSONUtils.class.getSimpleName();

    private static final String KEY_LINE_ID = "id";
    private static final String KEY_LINE_NAME = "name";
    private static final String KEY_STATION_ID = "id";
    private static final String KEY_STATION_NAME = "name";

    public JSONUtils() {
    }

    public static ArrayList<Lines> extractFeatureFromJson(String lineJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(lineJSON)) {
            return null;
        }

        ArrayList<Lines> lines = new ArrayList<>();
        try {
            // Create a JSONObject from the JSON response string
            JSONArray lineArray = new JSONArray(lineJSON);

            // For each line in the recipeArray, create an {@link Recipes} object
            for (int i = 0; i < lineArray.length(); i++) {
                // Get a single recipe description at position i within the list of recipes
                JSONObject currentLine = lineArray.getJSONObject(i);

                String id = "";
                if (currentLine.has("id")) {
                    id = currentLine.optString(KEY_LINE_ID);
                }

                String name = "";
                if (currentLine.has("name")) {
                    name = currentLine.optString(KEY_LINE_NAME);
                }
                Lines line = new Lines(id, name);
                lines.add(line);
            }
        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing lines JSON results", e);

        }
        // Return the list of lines
        return lines;
    }
}


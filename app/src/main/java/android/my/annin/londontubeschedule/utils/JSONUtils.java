package android.my.annin.londontubeschedule.utils;

import android.my.annin.londontubeschedule.model.Lines;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils
{
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = JSONUtils.class.getSimpleName();

    private static final String KEY_LINE_ID = "id";
    private static final String KEY_LINE_NAME = "name";


    public JSONUtils()
    {
    }

    public static Lines extractFeatureFromJson (String linesJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(linesJSON)) {
            return null;
        }

        Lines line = null;
        try
        {
            // Create a JSONObject from the JSON file
            JSONObject jsonObject = new JSONObject(linesJSON);

            String id = "";
            if (jsonObject.has("id"))
            {
                id = jsonObject.optString(KEY_LINE_ID);
            }

            String name = "";
            if (jsonObject.has("name"))
            {
                name= jsonObject.optString(KEY_LINE_NAME);
            }

            line = new Lines(id, name);
    }
        catch (JSONException e)
    {
        // If an error is thrown when executing any of the above statements in the "try" block,
        // catch the exception here, so the app doesn't crash. Print a log message
        // with the message from the exception.
        Log.e("QueryUtils", "Problem parsing lines JSON results", e);

    }
        // Return the list of lines
        return line;
}
}

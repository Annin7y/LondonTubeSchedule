package capstone.my.annin.londontubeschedule.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.Lines;
import capstone.my.annin.londontubeschedule.pojo.Schedule;
import capstone.my.annin.londontubeschedule.pojo.Stations;
import timber.log.Timber;

public class JSONUtils
{
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = JSONUtils.class.getSimpleName();

    private static final String KEY_LINE_ID = "id";
    private static final String KEY_LINE_NAME = "name";
    private static final String KEY_LINE_STATUS_DESC = "statusSeverityDescription";
    private static final String KEY_LINE_STATUS_REASON = "reason";
    private static final String KEY_STATION_ID = "id";
    private static final String KEY_STATION_NAME = "name";
    private static final String KEY_STATION_NAPTAN_ID = "naptanId";
    private static final String KEY_STATION_SCHEDULE_NAME = "stationName";
    private static final String KEY_STATION_LOCATION_LAT = "lat";
    private static final String KEY_STATION_LOCATION_LON = "lon";
    private static final String KEY_DESTINATION_NAME = "destinationName";
    private static final String KEY_CURRENT_LOCATION = "currentLocation";
    private static final String KEY_DIRECTION_TOWARDS = "towards";
    private static final String KEY_EXPECTED_ARRIVAL = "expectedArrival";

    public JSONUtils()
    {
    }

    //Lines code commented out replaced by the one below which includes service updates
//    public static ArrayList<Lines> extractFeatureFromLineJson(String lineJSON)
//    {
//        // If the JSON string is empty or null, then return early.
//        if (TextUtils.isEmpty(lineJSON))
//        {
//            return null;
//        }
//
//        ArrayList<Lines> lines = new ArrayList<>();
//        try
//        {
//            // Create a JSONObject from the JSON response string
//            JSONArray lineArray = new JSONArray(lineJSON);
//
//            // For each line in the recipeArray, create an {@link Lines} object
//            for (int i = 0; i < lineArray.length(); i++)
//            {
//                // Get a single line description at position i within the list of lines
//                JSONObject currentLine = lineArray.getJSONObject(i);
//
//                String id = "";
//                if (currentLine.has("id"))
//                {
//                    id = currentLine.optString(KEY_LINE_ID);
//                }
//
//                String name = "";
//                if (currentLine.has("name"))
//                {
//                    name = currentLine.optString(KEY_LINE_NAME);
//                }
//                Lines line = new Lines(id, name);
//                lines.add(line);
//            }
//        }
//        catch (JSONException e)
//        {
//            // If an error is thrown when executing any of the above statements in the "try" block,
//            // catch the exception here, so the app doesn't crash. Print a log message
//            // with the message from the exception.
//            //Log.e("QueryUtils", "Problem parsing lines JSON results", e);
//            Timber.e("Problem parsing lines JSON results" );
//
//        }
//        // Return the list of lines
//        return lines;
//    }

    public static ArrayList<Lines> extractFeatureFromLineStatusJson(String lineStatusJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(lineStatusJSON))
        {
            return null;
        }

        ArrayList<Lines> lines = new ArrayList<>();
        try
        {
            // Create a JSONObject from the JSON response string
            JSONArray lineArray = new JSONArray(lineStatusJSON);

            // For each line in the recipeArray, create an {@link Lines} object
            for (int i = 0; i < lineArray.length(); i++)
            {
                // Get a single line description at position i within the list of lines
                JSONObject currentLine = lineArray.getJSONObject(i);

                String id = "";
                if (currentLine.has("id"))
                {
                    id = currentLine.optString(KEY_LINE_ID);
                }

                String name = "";
                if (currentLine.has("name"))
                {
                    name = currentLine.optString(KEY_LINE_NAME);
                }

                JSONArray lineStatusArrayList = currentLine.getJSONArray("lineStatuses");

                    if (lineStatusArrayList != null)
                    {
                        JSONObject innerElem = lineStatusArrayList.getJSONObject(0);
                                if (innerElem != null) {
                                    String lineStatusDesc = "";
                                    if (innerElem.has("statusSeverityDescription")) {
                                        lineStatusDesc = innerElem.optString(KEY_LINE_STATUS_DESC);
                                    }
                                    String lineStatusReason = "";
                                    if (innerElem.has("reason")) {
                                        lineStatusReason = innerElem.optString(KEY_LINE_STATUS_REASON);
                                    }

                                    Lines line = new Lines(id, name, lineStatusDesc, lineStatusReason);
                                lines.add(line);
        }}}}
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            //Log.e("QueryUtils", "Problem parsing lines JSON results", e);
            Timber.e("Problem parsing lines JSON results" );

        }
        // Return the list of lines
        return lines;
    }

    public static ArrayList<Stations> extractFeatureFromStationJson(String stationJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(stationJSON))
        {
            return null;
        }
        ArrayList<Stations> stations = new ArrayList<>();

        try
        {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(stationJSON);
            JSONArray stopPointSequenceArrayList = baseJsonResponse.getJSONArray("stopPointSequences");
            //Parsing structure below based on the accepted answer in this stackoverflow thread:
            //https://stackoverflow.com/questions/17673057/how-to-parse-this-nested-json-array-in-android
            if (stopPointSequenceArrayList != null)
            {
                for (int i = 0; i < stopPointSequenceArrayList.length(); i++)
                {
                    JSONObject elem = stopPointSequenceArrayList.getJSONObject(i);
                    if (elem != null)
                    {
                        JSONArray stopPointArrayList = elem.getJSONArray("stopPoint");
                        if (stopPointArrayList != null)
                        {
                            for (int j = 0; j < stopPointArrayList.length(); j++)
                            {
                                JSONObject innerElem = stopPointArrayList.getJSONObject(j);
                                if (innerElem != null)
                                {
                                    String idStation = "";
                                    if (innerElem.has("id"))
                                    {
                                        idStation = innerElem.optString(KEY_STATION_ID);
                                    }
                                    String nameStation = "";
                                    if (innerElem.has("name"))
                                    {
                                        nameStation = innerElem.optString(KEY_STATION_NAME);
                                    }
                                    double stationLatLocation = innerElem.getDouble("lat");
                                    if (innerElem.has("lat"))
                                    {
                                        stationLatLocation = innerElem.optDouble("lat");
                                    }
                                    double stationLonLocation = innerElem.getDouble("lon");
                                    if (innerElem.has("lon"))
                                    {
                                        stationLonLocation = innerElem.optDouble("lon");
                                    }

                                    Stations station = new Stations(idStation, nameStation, stationLatLocation, stationLonLocation);
                                    stations.add(station);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
       //Log.e("QueryUtils", "Problem parsing stations JSON results", e);
           Timber.e(e,"Problem parsing stations JSON results" );
        }
        // Return the list of stations
        return stations;
    }

    public static ArrayList<Schedule> extractFeatureFromScheduleJson(String scheduleJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(scheduleJSON))
        {
            return null;
        }

        ArrayList<Schedule> schedules = new ArrayList<>();

        try
        {
            // Create a JSONObject from the JSON response string
            JSONArray scheduleArray = new JSONArray(scheduleJSON);

            // For each schedule item in the scheduleArray, create an {@link Schedule} object
            for (int i = 0; i < scheduleArray.length(); i++)
            {
                // Get a single schedule description at position i within the schedule list
                JSONObject currentSchedule = scheduleArray.getJSONObject(i);

                //Extract values for the following keys
                String naptanIdStation = "";
                if (currentSchedule.has("naptanId"))
                {
                    naptanIdStation = currentSchedule.optString(KEY_STATION_NAPTAN_ID);
                }

                String scheduleNameStation = "";
                if (currentSchedule.has("stationName"))
                {
                    scheduleNameStation = currentSchedule.optString(KEY_STATION_SCHEDULE_NAME);
                }

                String nameDestination = "";
                if (currentSchedule.has("destinationName"))
                {
                    nameDestination = currentSchedule.optString(KEY_DESTINATION_NAME);
                }

                String locationCurrent = "";
                if (currentSchedule.has("currentLocation"))
                {
                    locationCurrent = currentSchedule.optString(KEY_CURRENT_LOCATION);
                }

                String towardsDirection = "";
                if (currentSchedule.has("towards"))
                {
                    towardsDirection = currentSchedule.optString(KEY_DIRECTION_TOWARDS);
                }

                String arrivalExpected = "";
                if (currentSchedule.has("expectedArrival"))
                {
                    arrivalExpected = currentSchedule.optString(KEY_EXPECTED_ARRIVAL);
                }

                Schedule schedule = new Schedule(naptanIdStation, scheduleNameStation, nameDestination, locationCurrent, towardsDirection, arrivalExpected);
                schedules.add(schedule);
            }
        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            //Log.e("QueryUtils", "Problem parsing schedule JSON results", e);
            Timber.e(e,"Problem parsing schedule JSON results" );

        }

        //Return the schedule list
        return schedules;
    }
}

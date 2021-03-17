/* Copyright 2020 Anastasia Annin

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package capstone.my.annin.londontubeschedule.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;

import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.pojo.OvergroundSchedule;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStation;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStatus;
import capstone.my.annin.londontubeschedule.pojo.Schedule;
import capstone.my.annin.londontubeschedule.pojo.Station;
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
    private static final String KEY_PLATFORM_NAME = "platformName";
    private static final String KEY_OVERGROUND_ID ="id";
    private static final String KEY_OVERGROUND_NAME = "name";
    private static final String KEY_OVERGROUND_STATUS_DESC = "statusSeverityDescription";
    private static final String KEY_OVERGROUND_STATUS_REASON = "reason";

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

    public static ArrayList<Line> extractFeatureFromLineStatusJson(String lineStatusJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(lineStatusJSON))
        {
            return null;
        }

        ArrayList<Line> lines = new ArrayList<>();
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

                                    Line line = new Line(id, name, lineStatusDesc, lineStatusReason);
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

    public static ArrayList<Station> extractFeatureFromStationJson(String stationJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(stationJSON))
        {
            return null;
        }
        ArrayList<Station> stations = new ArrayList<>();

        try
        {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(stationJSON);
            JSONArray stopPointSequenceArrayList = baseJsonResponse.getJSONArray("stopPointSequences");
            //Parsing structure below based on the accepted answer in this stackoverflow thread:
            //https://stackoverflow.com/questions/17673057/how-to-parse-this-nested-json-array-in-android
            if (stopPointSequenceArrayList != null)
            {
                LinkedHashSet<Station> linkedHashSet = new LinkedHashSet<>();
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

                                    Station station = new Station(idStation, nameStation, stationLatLocation, stationLonLocation);
                                    //stations.add(station);
                                    //Code based on the following stackoverflow post:
                                    //https://stackoverflow.com/questions/47550315/remove-duplicated-in-base-unique-jsonobjects-in-jsonarray
                                    linkedHashSet.add(station);
                                    stations.clear();
                                    stations.addAll(linkedHashSet);
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



//    public static JSONArray extractRouteSequenceFromJson(String lineStatusJSON)
//    {
//        if (TextUtils.isEmpty(lineStatusJSON))
//        {
//            return null;
//        }
//        try
//        {
//            JSONObject jsonObject = new JSONObject(lineStatusJSON);
//            return jsonObject.getJSONArray("lineStrings");
//
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//            return null;
//        }
//    }

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

                String platformName = "";
                if (currentSchedule.has("platformName"))
                {
                    platformName = currentSchedule.optString(KEY_PLATFORM_NAME);
                }

                Schedule schedule = new Schedule(naptanIdStation, scheduleNameStation, nameDestination, locationCurrent, towardsDirection, arrivalExpected, platformName);
                schedules.add(schedule);
            }
            //Code based on the 1st answer in the following stackoverflow post:
            //https://stackoverflow.com/questions/17697568/how-to-sort-jsonarray-in-android/17698236
            Collections.sort(schedules, new Comparator<Schedule>()
            {
              @Override
               public int compare(Schedule o1, Schedule o2)
               {
                  String time1 = o1.getExpectedArrival();
                  String time2 = o2.getExpectedArrival();
                  return time1.compareTo(time2);
               }
           });
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

    public static ArrayList<OvergroundStatus> extractFeatureFromOvergroundStatusJson(String overgroundStatusJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(overgroundStatusJSON))
        {
            return null;
        }

        ArrayList<OvergroundStatus> overgroundLines = new ArrayList<>();
        try
        {
            // Create a JSONObject from the JSON response string
            JSONArray overgroundArray = new JSONArray(overgroundStatusJSON);

            // For each line in the recipeArray, create an {@link Lines} object
            for (int i = 0; i < overgroundArray.length(); i++)
            {
                // Get a single line description at position i within the list of lines
                JSONObject currentOverground = overgroundArray.getJSONObject(i);

                String id = "";
                if (currentOverground.has("id"))
                {
                    id = currentOverground.optString(KEY_OVERGROUND_ID);
                }

                String name = "";
                if (currentOverground.has("name"))
                {
                    name = currentOverground.optString(KEY_OVERGROUND_NAME);
                }

                JSONArray overgroundStatusArrayList = currentOverground.getJSONArray("lineStatuses");

                if (overgroundStatusArrayList != null)
                {
                    JSONObject innerElem = overgroundStatusArrayList.getJSONObject(0);
                    if (innerElem != null)
                    {
                        String modeStatusDesc = "";
                        if (innerElem.has("statusSeverityDescription"))
                        {
                            modeStatusDesc = innerElem.optString(KEY_OVERGROUND_STATUS_DESC);
                        }
                        String modeStatusReason = "";
                        if (innerElem.has("reason"))
                        {
                            modeStatusReason = innerElem.optString(KEY_OVERGROUND_STATUS_REASON);
                        }

                        OvergroundStatus overground = new OvergroundStatus(id, name, modeStatusDesc, modeStatusReason);
                        overgroundLines.add(overground);
                    }}}
        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            //Log.e("QueryUtils", "Problem parsing lines JSON results", e);
            Timber.e("Problem parsing overground lines JSON results" );

        }
        // Return the list of lines
        return overgroundLines;
    }
    public static ArrayList<OvergroundStation> extractFeatureFromOverStatJson(String stationJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(stationJSON))
        {
            return null;
        }
        ArrayList<OvergroundStation> stationsOver = new ArrayList<>();

        try
        {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(stationJSON);
            JSONArray stopPointSequenceArrayList = baseJsonResponse.getJSONArray("stopPointSequences");
            //Parsing structure below based on the accepted answer in this stackoverflow thread:
            //https://stackoverflow.com/questions/17673057/how-to-parse-this-nested-json-array-in-android
            if (stopPointSequenceArrayList != null)
            {
                LinkedHashSet<OvergroundStation> linkedHashSet = new LinkedHashSet<>();
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

                                    OvergroundStation station = new OvergroundStation(idStation, nameStation, stationLatLocation, stationLonLocation);
                                    //stations.add(station);
                                    //Code based on the following stackoverflow post:
                                    //https://stackoverflow.com/questions/47550315/remove-duplicated-in-base-unique-jsonobjects-in-jsonarray
                                    linkedHashSet.add(station);
                                    stationsOver.clear();
                                    stationsOver.addAll(linkedHashSet);
                                }
                            }
                        }
                    }
                }
            }
            Collections.sort(stationsOver, new Comparator<OvergroundStation>()
            {
                @Override
                public int compare(OvergroundStation o1, OvergroundStation o2)
                {
                    String station1 = o1.getStationName();
                    String station2 = o2.getStationName();
                    return station1.compareTo(station2);
                }
            });
        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            //Log.e("QueryUtils", "Problem parsing stations JSON results", e);
            Timber.e(e,"Problem parsing overground stations JSON results" );
        }
        // Return the list of overground stations
        return stationsOver;
    }

    public static ArrayList<OvergroundSchedule> extractFeatureFromOverSchJson(String overScheduleJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(overScheduleJSON))
        {
            return null;
        }

        ArrayList<OvergroundSchedule> overSchedules = new ArrayList<>();

        try
        {
            // Create a JSONObject from the JSON response string
            JSONArray overScheduleArray = new JSONArray(overScheduleJSON);

            // For each schedule item in the overScheduleArray, create an {@link OvergroundSchedule} object
            for (int i = 0; i < overScheduleArray.length(); i++)
            {
                // Get a single schedule description at position i within the schedule list
                JSONObject currentSchedule = overScheduleArray.getJSONObject(i);

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

                String arrivalExpected = "";
                if (currentSchedule.has("expectedArrival"))
                {
                    arrivalExpected = currentSchedule.optString(KEY_EXPECTED_ARRIVAL);
                }

                String platformName = "";
                if (currentSchedule.has("platformName"))
                {
                    platformName = currentSchedule.optString(KEY_PLATFORM_NAME);
                }

                OvergroundSchedule schedule = new OvergroundSchedule(naptanIdStation, scheduleNameStation, nameDestination, arrivalExpected, platformName);
                overSchedules.add(schedule);
            }
            //Code based on the 1st answer in the following stackoverflow post:
            //https://stackoverflow.com/questions/17697568/how-to-sort-jsonarray-in-android/17698236
            Collections.sort(overSchedules, new Comparator<OvergroundSchedule>()
            {
                @Override
                public int compare(OvergroundSchedule o1, OvergroundSchedule o2)
                {
                    String time1 = o1.getOverExpArrival();
                    String time2 = o2.getOverExpArrival();
                    return time1.compareTo(time2);
                }
            });
        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Timber.e(e,"Problem parsing overground schedule JSON results" );

        }
        //Return the overground schedule list
        return overSchedules;
    }
}

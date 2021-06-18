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

package capstone.my.annin.londontubeschedule.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.components.Component;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.pojo.OvergroundSchedule;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStation;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStatus;
import capstone.my.annin.londontubeschedule.pojo.Schedule;
import capstone.my.annin.londontubeschedule.pojo.Station;
import capstone.my.annin.londontubeschedule.ui.OverScheduleActivity;
import capstone.my.annin.londontubeschedule.ui.StationScheduleActivity;

public class ScheduleWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory
{
    private ArrayList<Schedule> mScheduleList;
    private ArrayList<Station> mStationList;
    private ArrayList<Line> mLineList;
    private Context mContext;
    private String stationWidgetArrivalTime;
    private Line lines;
    private Station stations;
    Schedule schedule;
    private ArrayList<OvergroundSchedule> mOverScheduleList;
    private ArrayList<OvergroundStation> mOverStationList;
    private ArrayList<OvergroundStatus> mOverStatusList;
    private String overStatWidgetArrivalTime;
    private OvergroundStatus overgroundStatus;
    private OvergroundStation overgroundStation;
    OvergroundSchedule overgroundSchedule;
    String packageName = "package capstone.my.annin.londontubeschedule.ui";
    private String overLineId;
    private String overModeName;
    private String overModeStatusDesc;
    private String overModeStatusReason;


    public ScheduleWidgetViewFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate()
    {
    }

    @Override
    public void onDataSetChanged()
    {
        //code structure based on the code in this link:
        //https://stackoverflow.com/questions/37927113/how-to-store-and-retrieve-an-object-from-gson-in-android
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        Gson gson = new Gson();
        Type type = new TypeToken<List<Schedule>>()
        {
        }.getType();
        String gsonString = sharedPreferences.getString("ScheduleList_Widget", "");
        mScheduleList = gson.fromJson(gsonString, type);

        Type typeLine = new TypeToken<List<Line>>()
        {
        }.getType();
        String gsonStringLine = sharedPreferences.getString("LineList_Widget", "");
        mLineList = gson.fromJson(gsonStringLine, typeLine);

        Type typeStation = new TypeToken<List<Station>>()
        {
        }.getType();
        String gsonStringStation = sharedPreferences.getString("StationList_Widget", "");
        mStationList = gson.fromJson(gsonStringStation, typeStation);

        //Extract the JSON lines from preferences and assign it to a Lines object.
        String jsonLines = sharedPreferences.getString("Lines", "");
        lines = gson.fromJson(jsonLines, Line.class);

        //Extract the JSON stations from preferences and assign it to a Stations object.
        String jsonStations = sharedPreferences.getString("Stations", "");
        stations = gson.fromJson(jsonStations, Station.class);

        Type typeOverSch = new TypeToken<List<OvergroundSchedule>>()
        {
        }.getType();
        String gsonOverSchString = sharedPreferences.getString("OverScheduleList_Widget", "");
        mOverScheduleList = gson.fromJson(gsonOverSchString, typeOverSch);

        Type typeOverStatus = new TypeToken<List<OvergroundStatus>>()
        {
        }.getType();
        String gsonOverStatString = sharedPreferences.getString("OverStatusList_Widget", "");
        mOverStatusList = gson.fromJson(gsonOverStatString, typeOverStatus);

        //Extract the JSON lines from preferences and assign it to an OverStatus object.
//        String jsonOverStatus = sharedPreferences.getString("OverStatus", "");
//        overgroundStatus = gson.fromJson(jsonOverStatus, OvergroundStatus.class);

       String jsonOverLineId = sharedPreferences.getString("OverLineId", "");
       overLineId = gson.fromJson(jsonOverLineId, String.class);

        String jsonOverModeName = sharedPreferences.getString("OverModeName", "");
        overModeName = gson.fromJson(jsonOverModeName, String.class);

        String jsonOverModeDesc = sharedPreferences.getString("OverModeDesc", "");
        overModeStatusDesc = gson.fromJson(jsonOverModeDesc, String.class);

        String jsonOverModeReason = sharedPreferences.getString("OverModeReason", "");
        overModeStatusReason = gson.fromJson(jsonOverModeReason, String.class);

        Type typeOverStation = new TypeToken<List<OvergroundStation>>()
        {
        }.getType();
        String gsonOverStringStation = sharedPreferences.getString("OverStationList_Widget", "");
        mOverStationList = gson.fromJson(gsonOverStringStation, typeOverStation);


        //Extract the JSON stations from preferences and assign it to an Overground Stations object.
        String jsonOverStations = sharedPreferences.getString("OverStations", "");
        overgroundStation = gson.fromJson(jsonOverStations, OvergroundStation.class);


    }

    @Override
    public int getCount()
    {
        return mScheduleList.size() + mOverScheduleList.size();
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.schedule_widget_list_item);
        if(position < mScheduleList.size())
        {
            Schedule schedule = mScheduleList.get(position);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = null;

            try
            {
                date = simpleDateFormat.parse(schedule.getExpectedArrival());
                //  date.toString();
                if (date != null)
                {
                    long timeInMilliseconds = date.getTime();

                    //Convert the date to a relative future date("in 4 minutes"); code based on this example:
                    //https://stackoverflow.com/questions/49441035/dateutils-getrelativetimespanstring-returning-a-formatted-date-string-instead-of
                    CharSequence relativeDate = DateUtils.getRelativeTimeSpanString(timeInMilliseconds,
                            System.currentTimeMillis(),
                            DateUtils.MINUTE_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_RELATIVE);

                    //convert CharSequence to String; Based on the following StackOverflow post:
                    // https://stackoverflow.com/questions/35305236/converting-from-charsequence-to-string-in-java
                    String futureDate = String.valueOf(relativeDate);
                    // futureDate.toString();
                    stationWidgetArrivalTime = futureDate;
                }

            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            //Code commented out; relative date implemented
            // SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
            //  String finalDate = newDateFormat.format(date);
            // stationWidgetArrivalTime = finalDate;

            // RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.schedule_widget_list_item);

            itemView.setTextViewText(R.id.schedule_widget_station_name, schedule.getStationScheduleName());
            itemView.setTextViewText(R.id.schedule_widget_arrival, stationWidgetArrivalTime);
            itemView.setTextViewText(R.id.schedule_widget_towards, schedule.getDirectionTowards());

            Intent intent = new Intent();
            intent.putExtra(ScheduleWidgetProvider.EXTRA_ITEM, schedule);
            intent.putExtra("Line", lines);
            intent.putExtra("Station", stations);
            intent.putParcelableArrayListExtra("stationList", mStationList);
            intent.putParcelableArrayListExtra("lineList", mLineList);
            itemView.setOnClickFillInIntent(R.id.schedule_widget_list, intent);

        }
        else
        {
            //Code based on the answer with 2 upvotes in the following stackoverflow post:
            //https://stackoverflow.com/questions/29788684/android-listview-adapter-with-two-arraylists
            OvergroundSchedule overgroundSchedule = mOverScheduleList.get(position - mScheduleList.size());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = null;

            try {
                date = simpleDateFormat.parse(overgroundSchedule.getOverExpArrival());
                //  date.toString();
                if (date != null)
                {
                    long timeInMilliseconds = date.getTime();

                    //Convert the date to a relative future date("in 4 minutes"); code based on this example:
                    //https://stackoverflow.com/questions/49441035/dateutils-getrelativetimespanstring-returning-a-formatted-date-string-instead-of
                    CharSequence relativeDate = DateUtils.getRelativeTimeSpanString(timeInMilliseconds,
                            System.currentTimeMillis(),
                            DateUtils.MINUTE_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_RELATIVE);

                    //convert CharSequence to String; Based on the following StackOverflow post:
                    // https://stackoverflow.com/questions/35305236/converting-from-charsequence-to-string-in-java
                    String futureDate = String.valueOf(relativeDate);
                    // futureDate.toString();
                    overStatWidgetArrivalTime = futureDate;
                }

            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            //Code commented out; relative date implemented
            // SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
            //  String finalDate = newDateFormat.format(date);
            // stationWidgetArrivalTime = finalDate;

            // RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.schedule_widget_list_item);

            itemView.setTextViewText(R.id.schedule_widget_station_name, overgroundSchedule.getOverStatSchName());
            itemView.setTextViewText(R.id.schedule_widget_arrival, stationWidgetArrivalTime);
            itemView.setTextViewText(R.id.schedule_widget_towards, overgroundSchedule.getOverDestName());

            Intent intent = new Intent();
            intent.putExtra(ScheduleWidgetProvider.EXTRA_ITEM, overgroundSchedule);
            //intent.putExtra("OvergroundStatus", overgroundStatus);
            intent.putExtra("OverLineId", overLineId);
            intent.putExtra("OverModeName", overModeName);
            intent.putExtra("OverModeDesc", overModeStatusDesc);
            intent.putExtra("OverModeReason", overModeStatusReason);
            intent.putExtra("OvergroundStation", overgroundStation);
            intent.putParcelableArrayListExtra("overStatusList", mOverStatusList);
            intent.putParcelableArrayListExtra("overStationList", mOverStationList);
            itemView.setOnClickFillInIntent(R.id.schedule_widget_list, intent);
        }

        return itemView;
    }


    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public RemoteViews getLoadingView()
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
    }
}





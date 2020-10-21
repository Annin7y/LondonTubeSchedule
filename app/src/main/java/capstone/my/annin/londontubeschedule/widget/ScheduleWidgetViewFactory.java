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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

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
import capstone.my.annin.londontubeschedule.pojo.Schedule;
import capstone.my.annin.londontubeschedule.pojo.Station;

public class ScheduleWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory
{
    private ArrayList<Schedule> mScheduleList;
    private ArrayList<Station> mStationList;
    private ArrayList<Line> mLineList;
    private Context mContext;
    private String stationWidgetArrivalTime;
    private Line lines;
    private Station stations;

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

    }

    @Override
    public int getCount()
    {
        return mScheduleList.size();
    }

    @Override
    public RemoteViews getViewAt(int position)
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

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.schedule_widget_list_item);

        itemView.setTextViewText(R.id.schedule_widget_station_name, schedule.getStationScheduleName());
        itemView.setTextViewText(R.id.schedule_widget_arrival, stationWidgetArrivalTime);
        itemView.setTextViewText(R.id.schedule_widget_towards, schedule.getDirectionTowards());

        Intent intent = new Intent();
        intent.putExtra(ScheduleWidgetProvider.EXTRA_ITEM, schedule);
        intent.putExtra("Line", lines);
        intent.putExtra("Station", stations);
        intent.putParcelableArrayListExtra("lineList", mLineList);
        intent.putParcelableArrayListExtra("stationList", mStationList);
        itemView.setOnClickFillInIntent(R.id.schedule_widget_list, intent);

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





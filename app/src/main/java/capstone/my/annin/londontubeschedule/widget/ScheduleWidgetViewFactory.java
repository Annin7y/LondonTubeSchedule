package capstone.my.annin.londontubeschedule.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Lines;
import capstone.my.annin.londontubeschedule.pojo.Schedule;

public class ScheduleWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory
{
    private ArrayList<Schedule> mScheduleList;
    private Context mContext;
    private String stationWidgetArrivalTime;
    private Lines lines;

    public ScheduleWidgetViewFactory(Context context)
    {
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
        Type type = new TypeToken<List<Schedule>>() {}.getType();
        String gsonString = sharedPreferences.getString("ScheduleList_Widget", "");
        mScheduleList = gson.fromJson(gsonString, type);
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
        Date date = null;

        try
        {
            date = simpleDateFormat.parse(schedule.getExpectedArrival());
            date.toString();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        String finalDate = newDateFormat.format(date);

        stationWidgetArrivalTime = finalDate;

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.schedule_widget_list_item);

        itemView.setTextViewText(R.id.schedule_widget_station_name, schedule.getStationScheduleName());
        itemView.setTextViewText(R.id.schedule_widget_arrival, stationWidgetArrivalTime);
        itemView.setTextViewText(R.id.schedule_widget_towards, schedule.getDirectionTowards());

        Intent intent = new Intent();
        intent.putExtra(ScheduleWidgetProvider.EXTRA_ITEM, schedule);
        intent.putExtra(ScheduleWidgetProvider.EXTRA_ITEM, lines);
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





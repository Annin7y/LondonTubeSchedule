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
import java.util.ArrayList;
import java.util.List;

import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.model.Schedule;

public class ScheduleWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory
{
    private ArrayList<Schedule> mScheduleList;
    private Context mContext;

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
        //code structure based on this link:
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

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.schedule_list_item);

        itemView.setTextViewText(R.id.schedule_station_name, schedule.getStationScheduleName());
        itemView.setTextViewText(R.id.schedule_arrival, schedule.getExpectedArrival());

        Intent intent = new Intent();
        intent.putExtra(ScheduleWidgetProvider.EXTRA_ITEM, schedule);
        itemView.setOnClickFillInIntent(R.id.schedule_list, intent);

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
    public void onDestroy() {
    }
}





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

package capstone.my.annin.londontubeschedule.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.TubeScheduleAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeScheduleAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.maps.MapsConnectionCheck;
import capstone.my.annin.londontubeschedule.maps.StationMapActivity;
import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.pojo.Schedule;
import capstone.my.annin.londontubeschedule.pojo.Station;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.ScheduleAdapter;
import capstone.my.annin.londontubeschedule.widget.ScheduleWidgetProvider;
import timber.log.Timber;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.ShareActionProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StationScheduleActivity extends AppCompatActivity implements TubeScheduleAsyncTaskInterface
{
    private static final String TAG = StationScheduleActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_schedule)
    RecyclerView mScheduleRecyclerView;

    private ScheduleAdapter scheduleAdapter;
    private ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
    private static final String KEY_SCHEDULE_LIST = "schedule_list";
    Station station;
    private ArrayList<Station> stationArrayList = new ArrayList<>();
    private ArrayList<Station> lineArrayList = new ArrayList<>();
    private static final String KEY_STATION_LIST = "station_list";
    private static final String KEY_LINE_LIST = "line_list";
    public String stationId;
    Line line;
    public String lineId;
    private Context context;
    private ShareActionProvider mShareActionProvider;
    Schedule stationArrival;
    private String stationShareStationName;
    public String stationShareArrivalTime;
    private String stationShareDirection;
    @BindView(R.id.empty_view_schedule)
    TextView emptySchedule;
    private static final String KEY_EMPTY_VALUE = "empty_value";
    @BindView(R.id.extended_fab)
    ExtendedFloatingActionButton extendedFAB;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_schedule);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        scheduleAdapter = new ScheduleAdapter(scheduleArrayList, context);
        mScheduleRecyclerView.setAdapter(scheduleAdapter);

        RecyclerView.LayoutManager mScheduleLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mScheduleRecyclerView.setLayoutManager(mScheduleLayoutManager);

        if (getIntent() != null && getIntent().getExtras() != null)
        {
                line = getIntent().getExtras().getParcelable("Line");
                station = getIntent().getExtras().getParcelable("Station");

                lineId = line.getLineId();
               // Log.i("lineId: ", line.getLineId());
                Timber.v(line.getLineId(), "lineId: ");

                stationId = station.getStationId();
              // Log.i("stationId: ", stations.getStationId());
                Timber.v(station.getStationId(), "stationId: ");

            lineArrayList = getIntent().getParcelableArrayListExtra("lineList");
            stationArrayList = getIntent().getParcelableArrayListExtra("stationList");

                if (savedInstanceState == null)
                {

                /*
                 *  Starting the asyncTask so that schedule loads when the activity opens.
                 */
                TubeScheduleAsyncTask myScheduleTask = new TubeScheduleAsyncTask(this);
                myScheduleTask.execute(lineId, stationId);

            } else
                {
                //emptySchedule visibility code based on the answer in this stackoverflow thread:
                //https://stackoverflow.com/questions/51903851/keeping-textview-visibility-view-invisible-and-button-state-setenabledfalse
                if (savedInstanceState.getBoolean("visible"))
                {
                    emptySchedule.setVisibility(View.VISIBLE);
                }
                {
                    scheduleArrayList = savedInstanceState.getParcelableArrayList(KEY_SCHEDULE_LIST);
                    scheduleAdapter.setScheduleList(scheduleArrayList);
                }
            }
        }
            if (MapsConnectionCheck.checkPlayServices(this))
            {
                 init();
        }

        mScheduleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && extendedFAB.getVisibility() == View.VISIBLE)
                {
                    extendedFAB.hide();
                } else if (dy < 0 && extendedFAB.getVisibility() != View.VISIBLE)
                {
                    extendedFAB.show();
                }
            }
        });

    }

    @Override
    public void returnScheduleData(ArrayList<Schedule> simpleJsonScheduleData)
    {
        if (simpleJsonScheduleData.size() > 0)
        {
            scheduleAdapter = new ScheduleAdapter(simpleJsonScheduleData, StationScheduleActivity.this);
            scheduleArrayList = simpleJsonScheduleData;
            mScheduleRecyclerView.setAdapter(scheduleAdapter);
            scheduleAdapter.setScheduleList(scheduleArrayList);

            stationArrival = scheduleArrayList.get(0);

            stationShareStationName = stationArrival.getStationScheduleName();
            stationShareArrivalTime = stationArrival.getExpectedArrival();
            stationShareDirection = stationArrival.getDirectionTowards();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = null;

            try
            {
                date = simpleDateFormat.parse(stationArrival.getExpectedArrival());
                date.toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
            String finalDate = newDateFormat.format(date);

            stationShareArrivalTime = finalDate;

            //Store Schedule Info in SharedPreferences
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

            Gson gson = new Gson();
            String json = gson.toJson(scheduleArrayList);
            prefsEditor.putString("ScheduleList_Widget", json);

            //Save the Line as a JSON string using Preferences.
           String jsonLine = gson.toJson(line);
           prefsEditor.putString("Lines", jsonLine);

            //Save the Stations as a JSON string using Preferences.
            String jsonStation = gson.toJson(station);
            prefsEditor.putString("Stations", jsonStation);

            prefsEditor.apply();

            //Send to Widget Provider code based on the answer with 9 upvotes in this post:
            //https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
            Context context = getApplicationContext();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget = new ComponentName(context, ScheduleWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);


        } else
            {

            //Toast message commented out; replaced with the text message below
            // Toast.makeText(StationScheduleActivity.this, getString(R.string.empty_view_toast), Toast.LENGTH_SHORT).show();
            //Code below(and the trailer code) based on the highest rated answer in this stackoverflow post:
            //https://stackoverflow.com/questions/28217436/how-to-show-an-empty-view-with-a-recyclerview
            emptySchedule.setVisibility(View.VISIBLE);

        }
        if (mShareActionProvider != null)
        {
            mShareActionProvider.setShareIntent(createShareIntent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.schedule, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        return super.onCreateOptionsMenu(menu);
    }

    public Intent createShareIntent()
    {
        String shareTitle = "Next train at ";
        String data = shareTitle + "\n" + stationShareStationName + "\n" + stationShareArrivalTime + "\n" + stationShareDirection;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, data);
        return shareIntent;
    }

    private void init()
    {
      extendedFAB.setOnClickListener(new View.OnClickListener()
      {
          @Override
          public void onClick(View v)
          {
             Intent intent = new Intent(StationScheduleActivity.this, StationMapActivity.class);
              intent.putExtra("Line", line);
            intent.putExtra("Station", station);
            intent.putParcelableArrayListExtra("lineList", lineArrayList);
            intent.putParcelableArrayListExtra("stationList", stationArrayList);
             startActivity(intent);

          }
      });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (emptySchedule.getVisibility() == View.VISIBLE)
        {
            outState.putBoolean("visible", true);
        } else {
            outState.putBoolean("visible", false);
        }

        outState.putParcelableArrayList(KEY_SCHEDULE_LIST, scheduleArrayList);
        super.onSaveInstanceState(outState);
    }
}
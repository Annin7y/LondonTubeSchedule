package capstone.my.annin.londontubeschedule.ui;

import android.content.Context;
import android.content.Intent;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.TubeScheduleAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeScheduleAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.model.Lines;
import capstone.my.annin.londontubeschedule.model.Schedule;
import capstone.my.annin.londontubeschedule.model.Stations;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.ScheduleAdapter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StationScheduleActivity extends AppCompatActivity implements TubeScheduleAsyncTaskInterface {

    private static final String TAG = StationScheduleActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_schedule)
    RecyclerView mScheduleRecyclerView;

    private ScheduleAdapter scheduleAdapter;
    private ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
    private static final String KEY_SCHEDULE_LIST = "schedule_list";
    Stations stations;
    public String stationId;
    Lines lines;
    public String lineId;
    private Context context;
    private ShareActionProvider mShareActionProvider;
    Schedule stationArrival;
    private String stationShareStationName;
    private String stationShareArrivalTime;

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

        /*
         *  Starting the asyncTask so that schedule loads when the activity opens.
         */

        if (getIntent() != null && getIntent().getExtras() != null)
        {
            if (savedInstanceState == null)
            {
                stations = getIntent().getExtras().getParcelable("Stations");
                stationId = stations.getStationId();

                lines = getIntent().getExtras().getParcelable("Lines");
                lineId = lines.getLineId();

                TubeScheduleAsyncTask myScheduleTask = new TubeScheduleAsyncTask(this);
                myScheduleTask.execute(lineId,stationId);
            }
            else {
                scheduleArrayList = savedInstanceState.getParcelableArrayList(KEY_SCHEDULE_LIST);
                scheduleAdapter.setScheduleList(scheduleArrayList);
            }
            }
    }


    @Override
    public void returnScheduleData(ArrayList<Schedule> simpleJsonScheduleData)
    {
        if (simpleJsonScheduleData.size() > 0) {
            scheduleAdapter = new ScheduleAdapter(simpleJsonScheduleData, StationScheduleActivity.this);
            scheduleArrayList = simpleJsonScheduleData;
            mScheduleRecyclerView.setAdapter(scheduleAdapter);
            scheduleAdapter.setScheduleList(scheduleArrayList);

            stationArrival = scheduleArrayList.get(0);

            stationShareStationName = stationArrival.getStationScheduleName();
            stationShareArrivalTime = stationArrival.getExpectedArrival();

            //Store Ingredients in SharedPreferences
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

            Gson gson = new Gson();
            String json = gson.toJson(scheduleArrayList);
            prefsEditor.putString("ScheduleList_Widget", json);
            prefsEditor.apply();

        }
       else
        {
            Toast.makeText(StationScheduleActivity.this, "Data currently unavailable", Toast.LENGTH_SHORT).show();
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
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,stationShareStationName);
        shareIntent.putExtra(Intent.EXTRA_TEXT,stationShareArrivalTime);
        return shareIntent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_SCHEDULE_LIST, scheduleArrayList);
        super.onSaveInstanceState(outState);
    }}
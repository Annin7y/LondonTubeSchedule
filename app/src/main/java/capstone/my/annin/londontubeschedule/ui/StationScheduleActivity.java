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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        if (null != simpleJsonScheduleData) {
            scheduleAdapter = new ScheduleAdapter(simpleJsonScheduleData, StationScheduleActivity.this);
            scheduleArrayList = simpleJsonScheduleData;
        //    mScheduleRecyclerView.setAdapter(scheduleAdapter);
            scheduleAdapter.setScheduleList(scheduleArrayList);
        }
    }



//    public Intent createShareIntent()
//    {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, BASE_STATION_URL_SHARE  + stationKey);
//        return shareIntent;
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_SCHEDULE_LIST, scheduleArrayList);
        super.onSaveInstanceState(outState);
    }}
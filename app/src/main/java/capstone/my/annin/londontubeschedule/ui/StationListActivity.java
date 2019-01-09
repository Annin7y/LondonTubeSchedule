package capstone.my.annin.londontubeschedule.ui;

import capstone.my.annin.londontubeschedule.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.asynctask.TubeStationAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeStationAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.model.Lines;
import capstone.my.annin.londontubeschedule.model.Stations;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.StationsAdapter;

public class StationListActivity extends AppCompatActivity implements StationsAdapter.StationsAdapterOnClickHandler, TubeStationAsyncTaskInterface {
    //Tag for the log messages
    private static final String TAG = StationListActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_station)
    RecyclerView mStationRecyclerView;

    private StationsAdapter stationsAdapter;
    private ArrayList<Stations> stationsArrayList = new ArrayList<>();
    private static final String KEY_STATIONS_LIST = "stations_list";
    Lines lines;
    public String lineId;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        stationsAdapter = new StationsAdapter(this, stationsArrayList, context);
        mStationRecyclerView.setAdapter(stationsAdapter);

        RecyclerView.LayoutManager mStationLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mStationRecyclerView.setLayoutManager(mStationLayoutManager);

        /*
         *  Starting the asyncTask so that stations load when the activity opens.
         */
        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().getExtras() != null) {
                lines = getIntent().getExtras().getParcelable("Lines");
                lineId = lines.getLineId();

                TubeStationAsyncTask myStationTask = new TubeStationAsyncTask(this);
                myStationTask.execute(lineId);
            } else {
                stationsArrayList = savedInstanceState.getParcelableArrayList(KEY_STATIONS_LIST);
                stationsAdapter.setStationsList(stationsArrayList);
            }

        }
    }

    @Override
    public void returnStationData(ArrayList<Stations> simpleJsonStationData) {
        if (null != simpleJsonStationData) {
            stationsAdapter = new StationsAdapter(this, simpleJsonStationData, StationListActivity.this);
            stationsArrayList = simpleJsonStationData;
            mStationRecyclerView.setAdapter(stationsAdapter);
            stationsAdapter.setStationsList(stationsArrayList);
        }
    }

    @Override
    public void onClick(Stations stations) {
        Intent intent = new Intent(StationListActivity.this, StationScheduleActivity.class);
        intent.putExtra("Stations", stations);

        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_STATIONS_LIST, stationsArrayList);
        super.onSaveInstanceState(outState);
    }
}
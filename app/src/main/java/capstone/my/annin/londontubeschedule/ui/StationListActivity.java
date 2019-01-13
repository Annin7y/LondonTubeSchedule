package capstone.my.annin.londontubeschedule.ui;

import capstone.my.annin.londontubeschedule.R;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.asynctask.TubeStationAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeStationAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.data.TubeLineContract;
import capstone.my.annin.londontubeschedule.model.Lines;
import capstone.my.annin.londontubeschedule.model.Stations;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.StationsAdapter;

public class StationListActivity extends AppCompatActivity implements StationsAdapter.StationsAdapterOnClickHandler, TubeStationAsyncTaskInterface
{
    //Tag for the log messages
    private static final String TAG = StationListActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_station)
    RecyclerView mStationRecyclerView;

    private StationsAdapter stationsAdapter;
    private ArrayList<Stations> stationsArrayList = new ArrayList<>();
    private static final String KEY_STATIONS_LIST = "stations_list";
    private static final String KEY_LINE_NAME = "line_name";
    Lines lines;
    public String lineId;
    private Context context;
    private TextView lineNameStation;
    private String lineNameToString;

    @BindView(R.id.favorites_button)
    Button favoritesButton;

    /**
     * Identifier for the favorites data loader
     */
    private static final int FAVORITES_LOADER = 0;



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

        lineNameStation = (TextView) findViewById(R.id.line_name_station);

        //add to favorites
        favoritesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ContentValues values = new ContentValues();
                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_ID, lines.getLineId());
                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_NAME, lines.getLineName());
                Uri uri = getContentResolver().insert(TubeLineContract.TubeLineEntry.CONTENT_URI, values);

                if (uri != null)
                {
                    Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(StationListActivity.this, R.string.favorites_added, Toast.LENGTH_SHORT).show();
                    favoritesButton.setVisibility(View.GONE);
                }
            }

        });

        /*
         *  Starting the asyncTask so that stations load when the activity opens.
         */

            if (getIntent() != null && getIntent().getExtras() != null)
            {
                if (savedInstanceState == null)
                {
                lines = getIntent().getExtras().getParcelable("Lines");
                lineId = lines.getLineId();

                TubeStationAsyncTask myStationTask = new TubeStationAsyncTask(this);
                myStationTask.execute(lineId);

                lineNameStation.setText(lines.getLineName());

            } else
                {
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
        intent.putExtra("Lines", lines);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_STATIONS_LIST, stationsArrayList);
        super.onSaveInstanceState(outState);
    }
}
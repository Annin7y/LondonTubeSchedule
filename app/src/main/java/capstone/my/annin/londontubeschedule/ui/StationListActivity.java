package capstone.my.annin.londontubeschedule.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.TubeStationAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeStationAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.data.TubeLineContract;
import capstone.my.annin.londontubeschedule.pojo.Lines;
import capstone.my.annin.londontubeschedule.pojo.Stations;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.StationsAdapter;
import timber.log.Timber;

public class StationListActivity extends AppCompatActivity implements StationsAdapter.StationsAdapterOnClickHandler, TubeStationAsyncTaskInterface,
        LoaderManager.LoaderCallbacks<Cursor>
{
    //Tag for the log messages
    private static final String TAG = StationListActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_station)
    RecyclerView mStationRecyclerView;

    private StationsAdapter stationsAdapter;
    private ArrayList<Stations> stationsArrayList = new ArrayList<>();
    private static final String KEY_STATIONS_LIST = "stations_list";
    private static final String KEY_LINES_LIST = "lines_list";
    private static final String KEY_LINE_NAME = "line_name";
    Lines lines;
    public String lineId;
    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    @BindView(R.id.line_name_station)
    TextView lineNameStation;

    String lineNameToString;

    @BindView(R.id.favorites_button)
    Button favoritesButton;

    /**
     * Identifier for the favorites data loader
     */
    private static final int FAVORITES_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        stationsAdapter = new StationsAdapter(this, stationsArrayList, context);
        mStationRecyclerView.setAdapter(stationsAdapter);

        RecyclerView.LayoutManager mStationLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mStationRecyclerView.setLayoutManager(mStationLayoutManager);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //add to favorites
        favoritesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ContentValues values = new ContentValues();
                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_ID, lines.getLineId());
                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_NAME, lines.getLineName());
                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_STATUS_DESC, lines.getLineStatusDesc());
                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_STATUS_REASON, lines.getLineStatusReason());
                Uri uri = getContentResolver().insert(TubeLineContract.TubeLineEntry.CONTENT_URI, values);

                if (uri != null)
                {
                    Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(StationListActivity.this, R.string.favorites_added, Toast.LENGTH_SHORT).show();

                    favoritesButton.setVisibility(View.GONE);
                }
            }
        });

        if (getIntent() != null && getIntent().getExtras() != null)
        {
            if (savedInstanceState == null)
            {
                lines = getIntent().getExtras().getParcelable("Lines");
                lineId = lines.getLineId();
               // Log.i("lineId: ", lines.getLineId());
                Timber.i(lines.getLineId(), "lineId: ");

                lineNameStation.setText(lines.getLineName());
                lineNameToString = lineNameStation.getText().toString();
               // Log.i("lineName: ", lines.getLineName());
                Timber.i(lines.getLineName(),"lineName: ");


                /*
                 *  Starting the asyncTask so that stations load when the activity opens.
                 */
                TubeStationAsyncTask myStationTask = new TubeStationAsyncTask(this);
                myStationTask.execute(lineId);
            } else
                {
                stationsArrayList = savedInstanceState.getParcelableArrayList(KEY_STATIONS_LIST);
                stationsAdapter.setStationsList(stationsArrayList);
                lines = savedInstanceState.getParcelable(KEY_LINES_LIST);
                lineNameToString = savedInstanceState.getString(KEY_LINE_NAME);
                lineNameStation.setText(lineNameToString);
            }
        }
        // Kick off the loader
        getLoaderManager().initLoader(FAVORITES_LOADER, null, this);
    }

    @Override
    public void returnStationData(ArrayList<Stations> simpleJsonStationData)
    {
        if (null != simpleJsonStationData)
        {
            stationsAdapter = new StationsAdapter(this, simpleJsonStationData, StationListActivity.this);
            stationsArrayList = simpleJsonStationData;
            mStationRecyclerView.setAdapter(stationsAdapter);
            stationsAdapter.setStationsList(stationsArrayList);
        }
        else{
            Timber.e("Problem parsing stations JSON results" );
        }

    }

    @Override
    public void onClick(Stations stations)
    {
        Intent intent = new Intent(StationListActivity.this, StationScheduleActivity.class);
        intent.putExtra("Lines", lines);
        intent.putExtra("Stations", stations);
        startActivity(intent);

        //log event when the user selects a station
        Bundle params = new Bundle();
        params.putParcelable("station_select", stations);
        mFirebaseAnalytics.logEvent("station_select",params);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle)
    {
        String[] projection = {TubeLineContract.TubeLineEntry._ID, TubeLineContract.TubeLineEntry.COLUMN_LINES_ID,};
        String[] selectionArgs = new String[]{lineId};

        switch (loaderId)
        {
            case FAVORITES_LOADER:
                return new CursorLoader(this,   // Parent activity context
                        TubeLineContract.TubeLineEntry.CONTENT_URI,   // Provider content URI to query
                        projection,             // Columns to include in the resulting Cursor
                        TubeLineContract.TubeLineEntry.COLUMN_LINES_ID + "=?",
                        selectionArgs,
                        null);                  // Default sort order

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        if ((cursor != null) && (cursor.getCount() > 0))
        {
            //"Add to Favorites" button is disabled in the StationList Activity when the user clicks on a line stored in Favorites
            favoritesButton.setEnabled(false);
        }
    }

    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putParcelableArrayList(KEY_STATIONS_LIST, stationsArrayList);
        outState.putString(KEY_LINE_NAME, lineNameToString);
        outState.putParcelable(KEY_LINES_LIST, lines);
        super.onSaveInstanceState(outState);
    }
}
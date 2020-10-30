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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.TubeStationAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeStationAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.data.AppDatabase;
import capstone.my.annin.londontubeschedule.data.LineViewModel;
import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.pojo.Station;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.StationAdapter;
import timber.log.Timber;

//import capstone.my.annin.londontubeschedule.data.TubeLineContract;

public class StationListActivity extends AppCompatActivity implements StationAdapter.StationAdapterOnClickHandler, TubeStationAsyncTaskInterface
       // LoaderManager.LoaderCallbacks<Cursor>
{
    //Tag for the log messages
    private static final String TAG = StationListActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_station)
    RecyclerView mStationRecyclerView;

    private StationAdapter stationAdapter;
    private ArrayList<Station> stationArrayList = new ArrayList<>();
    private static final String KEY_STATION_LIST = "station_list";
    private ArrayList<Line> lineArrayList = new ArrayList<>();
    private static final String KEY_LINE_LIST = "line_list";
    private static final String KEY_LINE_NAME = "line_name";
    Line line;
    public String lineId;
    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    @BindView(R.id.line_name_station)
    TextView lineNameStation;

    String lineNameToString;

//    @BindView(R.id.favorites_button)
//    Button favoritesButton;

    // Create AppDatabase member variable for the Database
    // Member variable for the Database
    private AppDatabase mDb;

    //ViewModel variable
    private LineViewModel mLineViewModel;

    @BindView(R.id.empty_view_stations)
    TextView emptyStations;

    @BindView(R.id.extended_fab_add)
    ExtendedFloatingActionButton extendedFABAdd;

    @BindView(R.id.station_duplicate_instructions_two)
    TextView branchInstructions;

    // Keep track of whether the selected line is Favorite or not
    private boolean isFavorite;

    /**
     * Identifier for the favorites data loader
     */
    //private static final int FAVORITES_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        stationAdapter = new StationAdapter(this, stationArrayList, context);
        mStationRecyclerView.setAdapter(stationAdapter);

        RecyclerView.LayoutManager mStationLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mStationRecyclerView.setLayoutManager(mStationLayoutManager);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mDb = AppDatabase.getDatabase(getApplicationContext());

        mLineViewModel = ViewModelProviders.of(this).get(LineViewModel.class);

        // Set a click listener for the Favorite button
        extendedFABAdd.setOnClickListener(view ->
        {
            if (extendedFABAdd.getText().equals(getString(R.string.favorites_button_text_remove)))
            {
                mLineViewModel.delete(line);
                Toast.makeText(StationListActivity.this, getString(R.string.favorites_removed), Toast.LENGTH_SHORT).show();
                extendedFABAdd.setText(R.string.favorites_button_text_add);
            }
            else {
//             // If the line is not favorite, we add it to the DB
                mLineViewModel.insert(line);
                Toast.makeText(StationListActivity.this, R.string.favorites_added, Toast.LENGTH_SHORT).show();
                extendedFABAdd.setText((R.string.favorites_button_text_remove));

            }

        });

 //        Code used when declaring isFavorite in the Repository
//         favoritesButton.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                               public void onClick(View view) {
//
//                                                      if (isFavorite) {
//                                                        // If the movie is already favorite, we remove it from the DB
//                                                       mLineViewModel.delete(line).observe(StationListActivity.this, new Observer<Boolean>() {
//                                                            @Override
//                                                            public void onChanged(@Nullable Boolean isDeleteOk) {
//                                                                if (isDeleteOk != null && isDeleteOk) {
//                                                                    // If everything was OK,
//                                                                    // we change the button text and set isFavorite to false
//                                                                    Toast.makeText(StationListActivity.this, getString(R.string.favorites_removed), Toast.LENGTH_SHORT).show();
//                                                                    favoritesButton.setText(R.string.favorites_button_text_add);
//                                                                    isFavorite = false;
//                                                                }
//                                                            }
//                                                        });
//
//                                                    } else {
//                                                        // If the movie is not favorite, we add it to the DB
//                                                      mLineViewModel.insert(line).observe(StationListActivity.this, new Observer<Boolean>() {
//
//                                                           @Override
//                                                            public void onChanged(@Nullable Boolean isInsertOk) {
//                                                               if (isInsertOk != null && isInsertOk) {
//                                                                    if (isInsertOk) {
//                                                                      //  Toast.makeText(getBaseContext(), isInsertOk.toString(), Toast.LENGTH_LONG).show();
//                                                                        Toast.makeText(StationListActivity.this, R.string.favorites_added, Toast.LENGTH_SHORT).show();
//                                                                        favoritesButton.setVisibility(View.GONE);
//                                                                   }
//                                                                    // If everything was OK,
//                                                                    // we change the button text and set isFavorite to true
//                                                                    Toast.makeText(StationListActivity.this, R.string.favorites_added, Toast.LENGTH_SHORT).show();
//                                                                    favoritesButton.setText((R.string.favorites_removed));
//                                                                    isFavorite = true;
//                                                                }
//                                                            }
//                                                        });
//                                                    }
//                                                }
//                                            });


        if (getIntent() != null && getIntent().getExtras() != null)
        {
                line = getIntent().getExtras().getParcelable("Line");
                // Extract the line ID from the selected line
            if(line != null)
            {
                lineId = Objects.requireNonNull(line).getLineId();
                lineId = line.getLineId();
                // Log.i("lineId: ", lines.getLineId());
                Timber.i(line.getLineId(), "lineId: ");

                lineNameStation.setText(line.getLineName());
                lineNameToString = lineNameStation.getText().toString();
                // Log.i("lineName: ", line.getLineName());
                Timber.i(line.getLineName(), "lineName: ");
            }
                lineArrayList = getIntent().getParcelableArrayListExtra("lineList");


                //Code when setting isFavorite in the Repository
               // If the movie is favorite, we show the "Remove from Favorites" text.
                mLineViewModel.select(lineId);
                mLineViewModel.isFavorite().observe(this, isFavorite ->
                {
                    if (isFavorite)
                    {
                        extendedFABAdd.setText(getString(R.string.favorites_button_text_remove));

                    } else {
                        extendedFABAdd.setText(getString(R.string.favorites_button_text_add));
                    }
                });

                //The code below was used when running the Room Database on the main thread
              //   isFavorite = mLineViewModel.select(lineId);
//
//                // If the movie is favorite, we show the "Remove from Favorites" text.
//                // Otherwise, we show "Add to Favorites".
//           if (isFavorite)
//            {
//               favoritesButton.setText(getString(R.string.favorites_button_text_remove));
//           } else
//                {
//              favoritesButton.setText(getString(R.string.favorites_button_text_add));
//            }
//                mLineViewModel.select(lineId).observe(StationListActivity.this, new Observer<Line>()
//               {
//                    @Override
//                    public void onChanged(@Nullable Line line)
//                    {
//                        if (line!= null)
//                        {
//                            isFavorite = true;
//                            favoritesButton.setText(getString(R.string.favorites_button_text_remove));
//                        }
//                    }
//                });
//
//            } else
//                {
//                    favoritesButton.setText(getString(R.string.favorites_button_text_add));
//
//                    isFavorite = false;
//                           }


                /*
                 *  Starting the asyncTask so that stations load when the activity opens.
                 */
            if (savedInstanceState == null)
           {

                TubeStationAsyncTask myStationTask = new TubeStationAsyncTask(this);
                myStationTask.execute(lineId);
            } else
                {
                stationArrayList = savedInstanceState.getParcelableArrayList(KEY_STATION_LIST);
                stationAdapter.setStationList(stationArrayList);
                line = savedInstanceState.getParcelable(KEY_LINE_LIST);
                lineNameToString = savedInstanceState.getString(KEY_LINE_NAME);
                lineNameStation.setText(lineNameToString);
            }
        }
        mStationRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && extendedFABAdd.getVisibility() == View.VISIBLE)
                {
                    extendedFABAdd.hide();
                } else if (dy < 0 && extendedFABAdd.getVisibility() != View.VISIBLE)
                {
                    extendedFABAdd.show();
                }
            }
        });
        if(lineId.equals("bakerloo"))
        {
            branchInstructions.setText("Stations displayed in sequence between Harrow & Wealdstone and Elephane & Castle.");
        }


    }
    //add to favorites Content Provider code commented out
//        favoritesButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                ContentValues values = new ContentValues();
//                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_ID, lines.getLineId());
//                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_NAME, lines.getLineName());
//                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_STATUS_DESC, lines.getLineStatusDesc());
//                values.put(TubeLineContract.TubeLineEntry.COLUMN_LINES_STATUS_REASON, lines.getLineStatusReason());
//                Uri uri = getContentResolver().insert(TubeLineContract.TubeLineEntry.CONTENT_URI, values);
//
//                if (uri != null)
//                {
//                    Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
//                    Toast.makeText(StationListActivity.this, R.string.favorites_added, Toast.LENGTH_SHORT).show();
//
//                    favoritesButton.setVisibility(View.GONE);
//                }
//            }
//        });


        // Kick off the loader
       // getSupportLoaderManager().initLoader(FAVORITES_LOADER, null, this);


    @Override
    public void returnStationData(ArrayList<Station> simpleJsonStationData)
    {
        if (null != simpleJsonStationData)
        {
            stationAdapter = new StationAdapter(this, simpleJsonStationData, StationListActivity.this);
            stationArrayList = simpleJsonStationData;
            mStationRecyclerView.setAdapter(stationAdapter);
            stationAdapter.setStationList(stationArrayList);
        }
        else
            {
            Timber.e("Problem parsing stations JSON results" );
            emptyStations.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(Station station)
    {
        Intent intent = new Intent(StationListActivity.this, StationScheduleActivity.class);
        intent.putExtra("Line", line);
        intent.putExtra("Station", station);
        intent.putParcelableArrayListExtra("lineList", lineArrayList);
        intent.putParcelableArrayListExtra("stationList", stationArrayList);
        startActivity(intent);

        //log event when the user selects a station
        Bundle params = new Bundle();
        params.putParcelable("station_select", station);
        mFirebaseAnalytics.logEvent("station_select",params);

    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle)
//    {
//        String[] projection = {TubeLineContract.TubeLineEntry._ID, TubeLineContract.TubeLineEntry.COLUMN_LINES_ID,};
//        String[] selectionArgs = new String[]{lineId};
//
//        switch (loaderId)
//        {
//            case FAVORITES_LOADER:
//                return new CursorLoader(this,   // Parent activity context
//                        TubeLineContract.TubeLineEntry.CONTENT_URI,   // Provider content URI to query
//                        projection,             // Columns to include in the resulting Cursor
//                        TubeLineContract.TubeLineEntry.COLUMN_LINES_ID + "=?",
//                        selectionArgs,
//                        null);                  // Default sort order
//
//            default:
//                throw new RuntimeException("Loader Not Implemented: " + loaderId);
//        }
//    }
//
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
//    {
//        if ((cursor != null) && (cursor.getCount() > 0))
//        {
//            //"Add to Favorites" button is disabled in the StationList Activity when the user clicks on a line stored in Favorites
//            favoritesButton.setEnabled(false);
//        }
//    }
//
//    public void onLoaderReset(Loader<Cursor> cursorLoader)
//    {
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (emptyStations.getVisibility() == View.VISIBLE)
        {
            outState.putBoolean("visible", true);
        } else {
            outState.putBoolean("visible", false);
        }


        outState.putParcelableArrayList(KEY_STATION_LIST, stationArrayList);
        outState.putString(KEY_LINE_NAME, lineNameToString);
        outState.putParcelable(KEY_LINE_LIST, line);
        super.onSaveInstanceState(outState);
    }
}
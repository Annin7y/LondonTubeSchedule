package capstone.my.annin.londontubeschedule.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.data.TubeLineContract;
import capstone.my.annin.londontubeschedule.pojo.Lines;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.FavoritesAdapter;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.LinesAdapter;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

import static capstone.my.annin.londontubeschedule.data.TubeLineContentProvider.LOG_TAG;


public class MainActivity extends AppCompatActivity implements LinesAdapter.LinesAdapterOnClickHandler, TubeLineAsyncTaskInterface,
 LoaderManager.LoaderCallbacks<Cursor>
{

    // Tag for logging
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_main)
    RecyclerView mLineRecyclerView;
    private LinesAdapter linesAdapter;
    private ArrayList<Lines> linesArrayList = new ArrayList<>();
    private Context context;
    private static final String KEY_LINES_LIST = "lines_list";
    private static final String KEY_SORT_ORDER = "sort_order";
    private String selectedSortOrder = "line_list";
    private static final String SORT_BY_FAVORITES = "line_favorites";
    private static final String SORT_BY_LINES = "line_sort";
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    private AdView adView;
    private FavoritesAdapter favoritesAdapter;
    private static final int FAVORITES_LOADER_ID = 0;
    private int mPosition = RecyclerView.NO_POSITION;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        MobileAds.initialize(this, "ca-app-pub-9580291377897056~9472538876");
        AdRequest request = new AdRequest.Builder().build();
        adView = (AdView) findViewById(R.id.adView);
        adView.loadAd(request);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//
//        Bundle params = new Bundle();
//        params.putBundle("line_select", lines);
//        mFirebaseAnalytics.logEvent("ActivityStartEvent", bundle);
//
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);

        favoritesAdapter = new FavoritesAdapter(this, context);
        linesAdapter = new LinesAdapter(this, linesArrayList, context);
        mLineRecyclerView.setAdapter(linesAdapter);

        RecyclerView.LayoutManager mLineLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLineRecyclerView.setLayoutManager(mLineLayoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
            {
                if (viewHolder instanceof LinesAdapter.LinesAdapterViewHolder) return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir)
            {
                // Here is where you'll implement swipe to delete

                //Construct the URI for the item to delete
                //[Hint] Use getTag (from the adapter code) to get the id of the swiped item
                // Retrieve the id of the task to delete
                String id = (String) viewHolder.itemView.getTag();

                // Build appropriate uri with String row id appended
                String stringId = id;
                Uri uri = TubeLineContract.TubeLineEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                int rowsDeleted = getContentResolver().delete(uri, null, null);
                Log.v("CatalogActivity", rowsDeleted + " rows deleted from the line database");

                getSupportLoaderManager().restartLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
            }
        }).attachToRecyclerView(mLineRecyclerView);

        /*
         *  Starting the asyncTask so that lines load upon launching the app.
         */
        if (savedInstanceState == null)
        {
            if (isNetworkStatusAvailable(this))
            {
                TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(this);
                myLineTask.execute(NetworkUtils.buildLineUrl());
            }
            else
                {
                Snackbar
                        .make(mCoordinatorLayout, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new MyClickListener())
                        .show();
            }
        }
        else
        {
          selectedSortOrder = savedInstanceState.getString(KEY_SORT_ORDER, "line_list");
            if (selectedSortOrder == SORT_BY_FAVORITES)
            {
                getSupportLoaderManager().initLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
                mLineRecyclerView.setAdapter(favoritesAdapter);
            } else
                {
                linesArrayList = savedInstanceState.getParcelableArrayList(KEY_LINES_LIST);
                linesAdapter.setLinesList(linesArrayList);
            }
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        Log.v(LOG_TAG, "SORTORDER= " + selectedSortOrder);
        Log.i("list", linesArrayList.size() + "");
    }
    }

    public class MyClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            // Run the AsyncTask in response to the click
            TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(MainActivity.this);
            myLineTask.execute();
        }
    }

    @Override
    public void returnLineData(ArrayList<Lines> simpleJsonLineData)
    {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (null != simpleJsonLineData)
        {
            linesAdapter = new LinesAdapter(this, simpleJsonLineData, MainActivity.this);
            linesArrayList = simpleJsonLineData;
            mLineRecyclerView.setAdapter(linesAdapter);
            linesAdapter.setLinesList(linesArrayList);
        } else
            {
            showErrorMessage();
        }
    }

    @Override
    public void onClick(Lines lines)
    {
        Intent intent = new Intent(MainActivity.this, StationListActivity.class);
        intent.putExtra("Lines", lines);
        startActivity(intent);
    }

    //Display if there is no internet connection
    public void showErrorMessage()
    {
        Snackbar
                .make(mCoordinatorLayout, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new MyClickListener())
                .show();
        mLineRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    public static boolean isNetworkStatusAvailable(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs)
    {
        return new AsyncTaskLoader<Cursor>(this)
        {

            // Initialize a Cursor, this will hold all the task data
            Cursor mFavoritesData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading()
            {
                if (mFavoritesData != null)
                {
                    // Delivers any previously loaded data immediately
                    deliverResult(mFavoritesData);
                }
                else
                {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground()
            {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data
                try
                {
                    return getContentResolver().query(TubeLineContract.TubeLineEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                             TubeLineContract.TubeLineEntry.COLUMN_LINES_ID);
                }
                catch (Exception e)
                {
                    Log.e(LOG_TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data)
            {
                mFavoritesData = data;
                super.deliverResult(data);
            }
        };
    }

    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        favoritesAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mLineRecyclerView.smoothScrollToPosition(mPosition);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        favoritesAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(this);

        switch (item.getItemId())
        {
            case R.id.most_frequented_favorites:

                getSupportLoaderManager().restartLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
                favoritesAdapter = new FavoritesAdapter(this, MainActivity.this);
                mLineRecyclerView.setAdapter(favoritesAdapter);
                selectedSortOrder = SORT_BY_FAVORITES;
                return true;

            case R.id.line_list:
                myLineTask.execute();
                selectedSortOrder = SORT_BY_LINES;
                return true;

                default:
                return super.onOptionsItemSelected(item);
    }}

    @Override
    public void onResume()
    {
        super.onResume();
        TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(MainActivity.this);
        myLineTask.execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putString(KEY_SORT_ORDER, selectedSortOrder);
        outState.putParcelableArrayList(KEY_LINES_LIST, linesArrayList);
        super.onSaveInstanceState(outState);
    }
}
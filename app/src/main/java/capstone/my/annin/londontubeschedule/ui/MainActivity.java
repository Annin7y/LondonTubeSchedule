package capstone.my.annin.londontubeschedule.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.data.TubeLineContract;
import capstone.my.annin.londontubeschedule.model.Lines;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.FavoritesAdapter;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.LinesAdapter;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

import static capstone.my.annin.londontubeschedule.data.TubeLineContentProvider.LOG_TAG;


public class MainActivity extends AppCompatActivity implements LinesAdapter.LinesAdapterOnClickHandler, TubeLineAsyncTaskInterface {

    // Tag for logging
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_main)
    RecyclerView mLineRecyclerView;
    private LinesAdapter linesAdapter;
    private ArrayList<Lines> linesArrayList = new ArrayList<>();
    private Context context;
    private static final String KEY_LINES_LIST = "lines_list";
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    private AdView adView;
    private FavoritesAdapter favoritesAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        MobileAds.initialize(this, "ca-app-pub-9580291377897056~9472538876");
        AdRequest request = new AdRequest.Builder().build();
        adView = (AdView) findViewById(R.id.adView);
        adView.loadAd(request);

        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);

        linesAdapter = new LinesAdapter(this, linesArrayList, context);
        mLineRecyclerView.setAdapter(linesAdapter);

        RecyclerView.LayoutManager mLineLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLineRecyclerView.setLayoutManager(mLineLayoutManager);

        // mLineRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
         *  Starting the asyncTask so that lines load upon launching the app.
         */
        if (savedInstanceState == null) {
            if (isNetworkStatusAvailable(this)) {
                TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(this);
                myLineTask.execute(NetworkUtils.buildLineUrl());
            } else {
                Snackbar
                        .make(mCoordinatorLayout, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new MyClickListener())
                        .show();
            }
        } else {
            linesArrayList = savedInstanceState.getParcelableArrayList(KEY_LINES_LIST);
            linesAdapter.setLinesList(linesArrayList);
        }
    }

    public class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Run the AsyncTask in response to the click
            TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(MainActivity.this);
            myLineTask.execute();
        }
    }

    @Override
    public void returnLineData(ArrayList<Lines> simpleJsonLineData) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (null != simpleJsonLineData) {
            linesAdapter = new LinesAdapter(this, simpleJsonLineData, MainActivity.this);
            linesArrayList = simpleJsonLineData;
            mLineRecyclerView.setAdapter(linesAdapter);
            linesAdapter.setLinesList(linesArrayList);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onClick(Lines lines) {
        Intent intent = new Intent(MainActivity.this, StationListActivity.class);
        intent.putExtra("Lines", lines);
        startActivity(intent);
    }

    //Display if there is no internet connection
    public void showErrorMessage() {
        Snackbar
                .make(mCoordinatorLayout, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new MyClickListener())
                .show();
        mLineRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    public static boolean isNetworkStatusAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_LINES_LIST, linesArrayList);
        super.onSaveInstanceState(outState);
    }
}
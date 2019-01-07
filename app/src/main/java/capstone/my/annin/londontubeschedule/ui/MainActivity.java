package capstone.my.annin.londontubeschedule.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.model.Lines;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.LinesAdapter;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LinesAdapter.LinesAdapterOnClickHandler, TubeLineAsyncTaskInterface
{
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);

        linesAdapter = new LinesAdapter(this, linesArrayList, context);
        mLineRecyclerView.setAdapter(linesAdapter);

        mLineRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
         *  Starting the asyncTask so that lines load upon launching the app.
         */
        if (savedInstanceState == null)
        {
            if (isNetworkStatusAvailable(this))
            {
                TubeLineAsyncTask myTask = new TubeLineAsyncTask(this);
                myTask.execute(NetworkUtils.buildLineUrl());
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
            linesArrayList = savedInstanceState.getParcelableArrayList(KEY_LINES_LIST);
            linesAdapter.setLinesList(linesArrayList);
        }
    }

    public class MyClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            // Run the AsyncTask in response to the click
            TubeLineAsyncTask myTask = new TubeLineAsyncTask(MainActivity.this);
            myTask.execute();
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
        }
        else
        {
            showErrorMessage();
        }
    }

    @Override
    public void onClick(Lines lines)
    {
        Intent intent = new Intent(MainActivity.this, StationListActivity.class);
        intent.putExtra("Lines", lines);
        //the animation transition explode code is based on the third answer in the following stackoverflow post:
        //https://stackoverflow.com/questions/24517620/activityoptions-makescenetransitionanimation-doesnt-seem-to-exist
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
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putParcelableArrayList(KEY_LINES_LIST, linesArrayList);
        super.onSaveInstanceState(outState);
    }
}

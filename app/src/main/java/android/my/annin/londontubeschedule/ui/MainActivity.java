package android.my.annin.londontubeschedule.ui;

import android.content.Context;
import android.my.annin.londontubeschedule.R;
import android.my.annin.londontubeschedule.asynctask.TubeLineAsyncTask;
import android.my.annin.londontubeschedule.asynctask.TubeLineAsyncTaskInterface;
import android.my.annin.londontubeschedule.model.Lines;
import android.my.annin.londontubeschedule.recyclerviewadapters.LinesAdapter;
import android.my.annin.londontubeschedule.utils.NetworkUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        // Bind the views
        ButterKnife.bind(this);

        linesAdapter = new LinesAdapter(this, linesArrayList, context);
        mLineRecyclerView.setAdapter(linesAdapter);

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

    //Display if there is no internet connection
    public void showErrorMessage()
    {
        Snackbar
                .make(mCoordinatorLayout, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new MyClickListener())
                .show();
        mRecyclerView.setVisibility(View.INVISIBLE);
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

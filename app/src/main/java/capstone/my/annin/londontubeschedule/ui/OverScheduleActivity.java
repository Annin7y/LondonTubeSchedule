
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

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundSchAllAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundSchAllAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundScheduleAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundScheduleAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundStatAllAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundStatAllAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundStationAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundStationAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundStatusAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundStatusAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.maps.MapsConnectionCheck;
import capstone.my.annin.londontubeschedule.maps.StationMapActivity;
import capstone.my.annin.londontubeschedule.pojo.OvergroundSchedule;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStation;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStatus;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.OvergroundScheduleAdapter;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.OvergroundStationAdapter;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.OvergroundStatusAdapter;
import capstone.my.annin.londontubeschedule.scrollbehavior.DisableSwipeBehavior;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;
import capstone.my.annin.londontubeschedule.widget.ScheduleWidgetProvider;
import timber.log.Timber;

//import static capstone.my.annin.londontubeschedule.ui.MyClickListener.isNetworkStatusAvailable;

public class OverScheduleActivity extends AppCompatActivity implements OvergroundScheduleAsyncTaskInterface,OvergroundStationAdapter.OvergroundStationAdapterOnClickHandler, OvergroundStatAllAsyncTaskInterface, OvergroundSchAllAsyncTaskInterface,
        OvergroundStatusAdapter.OvergroundStatusAdapterOnClickHandler, OvergroundStatusAsyncTaskInterface
{
    private static final String TAG = OverScheduleActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_over_schedule)
    RecyclerView mScheduleRecyclerView;

    private OvergroundScheduleAdapter overSchAdapter;
    private ArrayList<OvergroundSchedule> overSchArrayList = new ArrayList<>();
    ArrayList<OvergroundSchedule> filteredList = new ArrayList<OvergroundSchedule>();
    private static final String KEY_OVER_SCHEDULE_LIST = "schedule_list";
    OvergroundStation overgroundStation;
    private ArrayList<OvergroundStation> overStatArrayList = new ArrayList<>();
    private ArrayList<OvergroundStatus> overgroundStatusArrayList = new ArrayList<>();
    private static final String KEY_STATION_LIST = "station_list";
    private static final String KEY_STATUS_LIST = "status_list";
    public String stationOverId;
    public String stationOverIdAll;
    OvergroundStatus overground;
    OvergroundStatus overgroundObject;
    private Context context;
    private ShareActionProvider mShareActionProvider;
    OvergroundSchedule stationArrival;
    private String stationShareOverStationName;
    public String stationShareOverArrivalTime;

    private String stationShareDirection;
    @BindView(R.id.empty_view_schedule)
    TextView emptySchedule;
    String stationNameToString;
    String stationNameToStringAuto;
    String autoCompleteText;
    private static final String KEY_EMPTY_VALUE = "empty_value";
    @BindView(R.id.extended_fab)
    ExtendedFloatingActionButton extendedFAB;
    CoordinatorLayout mCoordinatorLayout;
    private boolean isSnackbarShowing = false;
    private static final String SNACKBAR_STATE = "snackbar_state";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    public double latLocationAll;
    public double lonLocationAll;
    private String latString;
    private String lonString;
    private boolean isAutoCompleteText = false;
    private Menu menu;
    public String overLineId;
    private String overModeName;
    private String overModeStatusDesc;
    private String overModeStatusReason;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_schedule);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        overSchAdapter = new OvergroundScheduleAdapter(overSchArrayList, context);
        mScheduleRecyclerView.setAdapter(overSchAdapter);

        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
        RecyclerView.LayoutManager mScheduleLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mScheduleRecyclerView.setLayoutManager(mScheduleLayoutManager);


     if (getIntent() != null && getIntent().getExtras() != null)
    {

       overground = getIntent().getExtras().getParcelable("OvergroundStatus");
//        overLineId = getIntent().getExtras().getString("OverLineId");
//        overModeName = getIntent().getExtras().getString("OverModeName");
//        overModeStatusDesc= getIntent().getExtras().getString("OverModeDesc");
//        overModeStatusReason = getIntent().getExtras().getString("OverModeReason");

        overgroundStation = getIntent().getExtras().getParcelable("OvergroundStation");

        stationNameToStringAuto = getIntent().getExtras().getString("OverStationName");
        stationOverIdAll = getIntent().getExtras().getString("OverStationIdAll");
        latString = getIntent().getExtras().getString("OverLat");
        lonString = getIntent().getExtras().getString("OverLon");

        autoCompleteText = getIntent().getExtras().getString("overStation");

        if(autoCompleteText != null && overground != null)
        {
//            OvergroundStatAllAsyncTask myOverStatTask = new OvergroundStatAllAsyncTask(this);
//            myOverStatTask.execute();

            OvergroundStatusAsyncTask myOvergroundTask = new OvergroundStatusAsyncTask(this);
            myOvergroundTask.execute(NetworkUtils.buildOvergroundStatusUrl());

        }
        else if (overground != null)
        // else if(overLineId != null && overModeName != null && overModeStatusDesc != null && overModeStatusReason != null)
        {
            overLineId= overground.getModeId();
//            // Log.i("lineId: ", line.getLineId());
            Timber.v(overground.getModeId(), "overLineId: ");

            if (overgroundStation != null)
            {
                stationOverId = overgroundStation.getStationId();
                // Log.i("stationId: ", stations.getStationId());
                Timber.v(overgroundStation.getStationId(), "stationId: ");

                // stationNameStation.setText(station.getStationName());
                stationNameToString = overgroundStation.getStationName();

                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(stationNameToString);

            }
            overgroundStatusArrayList = getIntent().getParcelableArrayListExtra("overgroundStatusList");
            overStatArrayList = getIntent().getParcelableArrayListExtra("overgroundStationList");


            if (savedInstanceState == null)
            {
                if (isNetworkStatusAvailable(this))
                {
                    /*
                     *  Starting the asyncTask so that schedule loads when the activity opens.
                     */
                    OvergroundScheduleAsyncTask myScheduleTask = new OvergroundScheduleAsyncTask(this);
                    myScheduleTask.execute(overLineId, stationOverId);


                } else
                {
                    Snackbar
                            .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.snackbar_retry, new OverScheduleActivity.MyClickListener())
                            .setBehavior(new DisableSwipeBehavior())
                            .show();
                    isSnackbarShowing = true;
                    showErrorMessage();
                }}

            else
            {
                isSnackbarShowing = savedInstanceState.getBoolean(SNACKBAR_STATE);
                if (isSnackbarShowing)
                {
                    Snackbar
                            .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.snackbar_retry, new OverScheduleActivity.MyClickListener())
                            .setBehavior(new DisableSwipeBehavior())
                            .show();
                }
                //emptySchedule visibility code based on the answer in this stackoverflow thread:
                //https://stackoverflow.com/questions/51903851/keeping-textview-visibility-view-invisible-and-button-state-setenabledfalse
                if (savedInstanceState.getBoolean("visible"))
                {

                    emptySchedule.setVisibility(View.VISIBLE);
                }
                {
                    overSchArrayList = savedInstanceState.getParcelableArrayList(KEY_OVER_SCHEDULE_LIST);
                    overSchAdapter.setOverSchList(overSchArrayList);
                }
            }
        }

//        else if(overLineId != null && overModeName != null && overModeStatusDesc != null && overModeStatusReason != null)
//        {
//
//            if(autoCompleteText != null && stationNameToStringAuto != null && stationOverIdAll != null  && latString != null && lonString != null && overLineId != null && overModeName != null && overModeStatusDesc != null && overModeStatusReason != null) {
////            OvergroundStatAllAsyncTask myOverStatTask = new OvergroundStatAllAsyncTask(this);
////            myOverStatTask.execute();
//
//                stationNameToString = stationNameToStringAuto;
//                ActionBar actionBar = getSupportActionBar();
//            actionBar.setTitle(stationNameToString);
//            }
//                overgroundStatusArrayList = getIntent().getParcelableArrayListExtra("overgroundStatusList");
//               overStatArrayList = getIntent().getParcelableArrayListExtra("overgroundStationList");
//
//                if (savedInstanceState == null)
//                {
//                    if (isNetworkStatusAvailable(this))
//                    {
//                        /*
//                         *  Starting the asyncTask so that schedule loads when the activity opens.
//                         */
//                       // OvergroundScheduleAsyncTask myScheduleTask = new OvergroundScheduleAsyncTask(this);
//                       // myScheduleTask.execute(overLineId, stationOverId);
////                        OvergroundSchAllAsyncTask myOverSchAllTask = new OvergroundSchAllAsyncTask(this);
////           myOverSchAllTask.execute();
//
//                        OvergroundSchAllAsyncTask myOverSchAllTask = new OvergroundSchAllAsyncTask(this);
//          myOverSchAllTask.execute();
//
//                    } else
//                    {
//                        Snackbar
//                                .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
//                                .setAction(R.string.snackbar_retry, new OverScheduleActivity.MyClickListener())
//                                .setBehavior(new DisableSwipeBehavior())
//                                .show();
//                        isSnackbarShowing = true;
//                        showErrorMessage();
//                    }}
//
//                else
//                {
//                    isSnackbarShowing = savedInstanceState.getBoolean(SNACKBAR_STATE);
//                    if (isSnackbarShowing)
//                    {
//                        Snackbar
//                                .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
//                                .setAction(R.string.snackbar_retry, new OverScheduleActivity.MyClickListener())
//                                .setBehavior(new DisableSwipeBehavior())
//                                .show();
//                    }
//                    //emptySchedule visibility code based on the answer in this stackoverflow thread:
//                    //https://stackoverflow.com/questions/51903851/keeping-textview-visibility-view-invisible-and-button-state-setenabledfalse
//                    if (savedInstanceState.getBoolean("visible"))
//                    {
//
//                        emptySchedule.setVisibility(View.VISIBLE);
//                    }
//                    {
//                        overSchArrayList = savedInstanceState.getParcelableArrayList(KEY_OVER_SCHEDULE_LIST);
//                        overSchAdapter.setOverSchList(overSchArrayList);
//                    }
//                }
//            }

        }

        if (MapsConnectionCheck.checkPlayServices(this))
        {
            init();
        }

        mScheduleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && extendedFAB.getVisibility() == View.VISIBLE)
                {
                    extendedFAB.hide();
                } else if (dy < 0 && extendedFAB.getVisibility() != View.VISIBLE)
                {
                    extendedFAB.show();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                refresh();
            }
        });

    }



    @Override
    public void returnOverScheduleData(ArrayList<OvergroundSchedule> simpleJsonOverSchData)
    {
        if (simpleJsonOverSchData.size() > 0)
        {
            overSchAdapter = new OvergroundScheduleAdapter(simpleJsonOverSchData, OverScheduleActivity.this);
            overSchArrayList = simpleJsonOverSchData;
            mScheduleRecyclerView.setAdapter(overSchAdapter);
            overSchAdapter.setOverSchList(overSchArrayList);

            stationArrival = overSchArrayList.get(0);

            stationShareOverStationName = stationArrival.getOverStatSchName();
            stationShareOverArrivalTime = stationArrival.getOverExpArrival();
            stationShareDirection = stationArrival.getOverDestName();

           menu.findItem(R.id.menu_item_share).setVisible(stationShareOverStationName != null && stationShareOverArrivalTime != null && stationShareDirection != null);


            swipeRefreshLayout.setRefreshing(false);
            //Store Schedule Info in SharedPreferences
           SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
           SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

           Gson gson = new Gson();
           String jsonOver = gson.toJson(overSchArrayList);
           prefsEditor.putString("OverScheduleList_Widget", jsonOver);

           String jsonOverStatusList = gson.toJson(overgroundStatusArrayList);
           prefsEditor.putString("OverStatusList_Widget", jsonOverStatusList);

           String jsonStationOverList = gson.toJson(overStatArrayList);
           prefsEditor.putString("OverStationList_Widget", jsonStationOverList);

           //Save the overgroundStatus as a JSON string using Preferences.
            String jsonStatus = gson.toJson(overground);
            prefsEditor.putString("OverStatus", jsonStatus);

//            String jsonModeId = gson.toJson(overLineId);
//            prefsEditor.putString("OverLineId", jsonModeId);
//
//            String jsonModeName = gson.toJson(overModeName);
//            prefsEditor.putString("OverModeName", jsonModeName);
//
//            String jsonModeDesc = gson.toJson(overModeStatusDesc);
//            prefsEditor.putString("OverModeDesc", jsonModeDesc);
//
//            String jsonModeReason = gson.toJson(overModeStatusReason);
//            prefsEditor.putString("OverModeReason", jsonModeReason);

           //Save the Overground Stations as a JSON string using Preferences.
            String jsonOverStation = gson.toJson(overgroundStation);
            prefsEditor.putString("OverStations", jsonOverStation);


            prefsEditor.apply();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            //convert time zone to London UK time(GMT)
            //Code based on the first answer in the following stackoverflow post:
            // https://stackoverflow.com/questions/22814263/how-to-set-the-timezone-for-string-parsing-in-android
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = null;
            try {
                //Relative date code based on this example:
                //https://stackoverflow.com/questions/49441035/dateutils-getrelativetimespanstring-returning-a-formatted-date-string-instead-of

                date = simpleDateFormat.parse(stationShareOverArrivalTime);
                SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
                newDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                stationShareOverArrivalTime = newDateFormat.format(date);
            }
            catch (ParseException e)
            {

                e.printStackTrace();
            }

            //Send to Widget Provider code based on the answer with 9 upvotes in this post:
            //https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
            Context context = getApplicationContext();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget = new ComponentName(context, ScheduleWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);


        } else
        {

            //Toast message commented out; replaced with the text message below
            // Toast.makeText(StationScheduleActivity.this, getString(R.string.empty_view_toast), Toast.LENGTH_SHORT).show();
            //Code below(and the trailer code) based on the highest rated answer in this stackoverflow post:
            //https://stackoverflow.com/questions/28217436/how-to-show-an-empty-view-with-a-recyclerview
            emptySchedule.setVisibility(View.VISIBLE);

        }
        if (mShareActionProvider != null)
        {
            mShareActionProvider.setShareIntent(createShareIntent());
        }
    }


    public class MyClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {

            if (isNetworkStatusAvailable(context))
            {
                OvergroundScheduleAsyncTask myScheduleTask = new OvergroundScheduleAsyncTask(OverScheduleActivity.this);
                myScheduleTask.execute(overLineId, stationOverId);
            }
            else
            {
                Snackbar
                        .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.snackbar_retry, new OverScheduleActivity.MyClickListener())
                        .setBehavior(new DisableSwipeBehavior())
                        .show();
                isSnackbarShowing = true;
                showErrorMessage();
            }
        }
    }

    @Override
    public void returnOverScheduleAllData(ArrayList<OvergroundSchedule> simpleJsonOverSchAllData)
    {
        if (simpleJsonOverSchAllData.size() > 0)
        {
            overSchAdapter = new OvergroundScheduleAdapter(simpleJsonOverSchAllData, OverScheduleActivity.this);
            overSchArrayList = simpleJsonOverSchAllData;
            mScheduleRecyclerView.setAdapter(overSchAdapter);
            overSchAdapter.setOverSchList(overSchArrayList);

            // stationArrival = overSchArrayList.get(0);

            //   stationShareOverStationName = stationArrival.getOverStatSchName();

            for (OvergroundSchedule overAllScheduleName : overSchArrayList)
            {
                if (autoCompleteText.equals(overAllScheduleName.getOverStatSchName()))
                {
                    // stationShareOverStationName = stationArrival.getOverStatSchName();
                    //   autoCompleteText = stationShareOverStationName;
                    isAutoCompleteText = true;
                    filteredList.add(overAllScheduleName);
                    stationArrival = filteredList.get(0);
                    Timber.v(overAllScheduleName.getOverStatSchName(), "Station name: ");
                    // break;
                } else {
                    Timber.e("Stations names null");

                }
            }
            mScheduleRecyclerView.setAdapter(overSchAdapter);
            overSchAdapter.setOverSchList(filteredList);
            overSchAdapter.notifyDataSetChanged();

              //  stationArrival = filteredList.get(0);


                if (stationArrival != null) {
                    stationShareOverStationName = stationArrival.getOverStatSchName();
                    stationShareOverArrivalTime = stationArrival.getOverExpArrival();
                    stationShareDirection = stationArrival.getOverDestName();

                    overSchArrayList = filteredList;

                    if (stationShareOverStationName != null && stationShareOverArrivalTime != null && stationShareDirection != null) {
                        menu.findItem(R.id.menu_item_share).setVisible(true);
                    } else {
                        menu.findItem(R.id.menu_item_share).setVisible(false);
                    }


                    swipeRefreshLayout.setRefreshing(false);
                    //Store Schedule Info in SharedPreferences
                    SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

                    Gson gson = new Gson();
                    String jsonOver = gson.toJson(overSchArrayList);
                    prefsEditor.putString("OverScheduleList_Widget", jsonOver);

                    String jsonOverStatusList = gson.toJson(overgroundStatusArrayList);
                    prefsEditor.putString("OverStatusList_Widget", jsonOverStatusList);

                    String jsonStationOverList = gson.toJson(overStatArrayList);
                    prefsEditor.putString("OverStationList_Widget", jsonStationOverList);

                    //Save the overgroundStatus as a JSON string using Preferences.
                    String jsonStatus = gson.toJson(overground);
                    prefsEditor.putString("OverStatus", jsonStatus);

                    // Save the Overground Stations as a JSON string using Preferences.
//                String jsonOverStation = gson.toJson(overgroundStation);
//                prefsEditor.putString("OverStations", jsonOverStation);

                String jsonOverLat = gson.toJson(latString);
                prefsEditor.putString("OverLat", jsonOverLat);

                String jsonOverLon = gson.toJson(lonString);
                prefsEditor.putString("OverLon", jsonOverLon);

                String jsonOverStationName = gson.toJson(stationNameToStringAuto);
                prefsEditor.putString("OverStationName", jsonOverStationName);

                String jsonOverStatId = gson.toJson(stationOverIdAll);
                prefsEditor.putString("OverStationIdAll", jsonOverStatId);

                String jsonOverLineId = gson.toJson(overLineId);
                prefsEditor.putString("OverLineId", jsonOverLineId);

                    String jsonOverAutoText = gson.toJson(autoCompleteText);
                    prefsEditor.putString("overStation", jsonOverAutoText);


                    prefsEditor.apply();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    //convert time zone to London UK time(GMT)
                    //Code based on the first answer in the following stackoverflow post:
                    // https://stackoverflow.com/questions/22814263/how-to-set-the-timezone-for-string-parsing-in-android
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date date = null;
                    try {
                        //Relative date code based on this example:
                        //https://stackoverflow.com/questions/49441035/dateutils-getrelativetimespanstring-returning-a-formatted-date-string-instead-of

                        date = simpleDateFormat.parse(stationShareOverArrivalTime);
                        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
                        newDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                        stationShareOverArrivalTime = newDateFormat.format(date);
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                    //Send to Widget Provider code based on the answer with 9 upvotes in this post:
                    //https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
                    Context context = getApplicationContext();
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    ComponentName thisWidget = new ComponentName(context, ScheduleWidgetProvider.class);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);

                }

        }
        else {
            Timber.e("Problem parsing all overground schedule JSON results");
            emptySchedule.setVisibility(View.VISIBLE);
        }
        if (mShareActionProvider != null)
        {
            mShareActionProvider.setShareIntent(createShareIntent());
        }
    }

    @Override
    public void returnOverStationAllData(ArrayList<OvergroundStation> simpleJsonOverStatData) {
        if ( simpleJsonOverStatData.size() > 0) {
           //  overStatAdapter = new OvergroundStationAdapter(this, simpleJsonOverStatData, OverScheduleActivity.this);
          //  mStationRecyclerView.setAdapter(overStatAdapter);
           // overStatAdapter.setStationList(overStatArrayList);
            overStatArrayList = simpleJsonOverStatData;
            //List<OvergroundStation> filteredList = new ArrayList<OvergroundStation>();
            for (OvergroundStation overstation : overStatArrayList)
            {
                if (overstation.getStationName().equals(autoCompleteText))
                {
                    latLocationAll = overstation.getLatLocation();
                    lonLocationAll = overstation.getLonLocation();
                    stationOverIdAll = overstation.getStationId();
                    stationNameToStringAuto = overstation.getStationName();
                    latString = String.valueOf(latLocationAll);
                    lonString = String.valueOf(lonLocationAll);
                    OvergroundSchAllAsyncTask myOverSchAllTask = new OvergroundSchAllAsyncTask(this);
                myOverSchAllTask.execute();
                   // Timber.i("Station name: ");
                    break;
                }
            }
//            if(autoCompleteText != null) {
//                OvergroundSchAllAsyncTask myOverSchAllTask = new OvergroundSchAllAsyncTask(this);
//                myOverSchAllTask.execute();
//            }
//            else {
//                Timber.e("Station name null");
//            }
        }
        else
            {
                Timber.e("Problem parsing overground stations JSON results");
                // emptyStations.setVisibility(View.VISIBLE);
            }
    }

    @Override
    public void onClick(OvergroundStation overgroundStation)
    {

    }

    @Override
    public void returnOvergroundData(ArrayList<OvergroundStatus> simpleJsonOvergroundData)
    {
        //mLoadingIndicator.setVisibility(View.INVISIBLE);
        if ( simpleJsonOvergroundData.size() > 0)
        {
            // overgroundStatusAdapter = new OvergroundStatusAdapter(this, simpleJsonOvergroundData, getContext());
            overgroundStatusArrayList = simpleJsonOvergroundData;

            //  mOvergroundStatusRecyclerView.setAdapter(overgroundStatusAdapter);
            //  overgroundStatusAdapter.setOvergroundList(overgroundStatusArrayList);
            for (OvergroundStatus overstatus : overgroundStatusArrayList)
            {
                overLineId = overstatus.getModeId();

                OvergroundStatAllAsyncTask myOverStatTask = new OvergroundStatAllAsyncTask(this);
                myOverStatTask.execute();

            }
                //   showErrorMessage();
                Timber.e("Problem parsing lines JSON results");

        }
    }

    @Override
    public void onClick(OvergroundStatus overground)
    {
        
    }


    //Display if there is no internet connection
    public void showErrorMessage()
    {
        Snackbar
                .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_retry, new OverScheduleActivity.MyClickListener())
                .setBehavior(new DisableSwipeBehavior())
                .show();
        // mLineRecyclerView.setVisibility(View.VISIBLE);
        //  mLoadingIndicator.setVisibility(View.VISIBLE);
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.schedule, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        this.menu = menu;

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.menu_item_share:
                createShareIntent();
                return true;

            case R.id.menu_item_refresh:
                refresh();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }}

    public Intent createShareIntent()
    {
        if(stationShareOverStationName != null && stationShareOverArrivalTime != null && stationShareDirection != null)
        {

            String shareTitle = "Next train at ";
            String data = shareTitle + "\n" + stationShareOverStationName + "\n" + stationShareOverArrivalTime + "\n" + stationShareDirection;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, data);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
            if(stationShareOverStationName != null && stationShareOverArrivalTime != null && stationShareDirection != null)
            {
                menu.findItem(R.id.menu_item_share).setVisible(true);
            }
            else
            {
                menu.findItem(R.id.menu_item_share).setVisible(false);
            }
            //isShareIntent = true;
            return shareIntent;

        }

        else
            {
//            String shareTitle2 = "Data Currently Unavailable ";
//            Intent shareIntent2 = new Intent(Intent.ACTION_SEND);
//            shareIntent2.setType("text/plain");
//            shareIntent2.putExtra(Intent.EXTRA_TEXT, shareTitle2);
//            startActivity(Intent.createChooser(shareIntent2, "Choose an app"));
//            return shareIntent2;
                return null;
        }
    }

    public void refresh() {
        //Code based on the following stackoverflow post:
        //https://stackoverflow.com/questions/36270993/reload-activity-from-same-activity
//        Intent intent = getIntent();
//        overridePendingTransition(0, 0);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(intent);


        if (isAutoCompleteText)
        {
            OvergroundSchAllAsyncTask myOverSchAllTask = new OvergroundSchAllAsyncTask(this);
            myOverSchAllTask.execute();

        }
        else
        {
            OvergroundScheduleAsyncTask myScheduleTask = new OvergroundScheduleAsyncTask(this);
            myScheduleTask.execute(overLineId, stationOverId);

        }

    }

    private void init()
    {
        extendedFAB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(OverScheduleActivity.this, StationMapActivity.class);
                intent.putExtra("OvergroundStatus", overground);
                intent.putExtra("OvergroundStation", overgroundStation);
                intent.putParcelableArrayListExtra("overgroundStatusList", overgroundStatusArrayList);
                intent.putParcelableArrayListExtra("overgroundStationList", overStatArrayList);
                intent.putExtra("latLocation", latLocationAll);
                intent.putExtra("lonLocation", lonLocationAll);
                intent.putExtra("overStation", autoCompleteText);

                startActivity(intent);

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (emptySchedule.getVisibility() == View.VISIBLE)
        {
            outState.putBoolean("visible", true);
        } else {
            outState.putBoolean("visible", false);
        }

        outState.putParcelableArrayList(KEY_OVER_SCHEDULE_LIST, overSchArrayList);
        outState.putBoolean(SNACKBAR_STATE, isSnackbarShowing);
        super.onSaveInstanceState(outState);
    }
}
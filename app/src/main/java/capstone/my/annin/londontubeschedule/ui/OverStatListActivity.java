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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundStationAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundStationAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStation;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStatus;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.OvergroundStationAdapter;
import timber.log.Timber;

public class OverStatListActivity extends AppCompatActivity implements OvergroundStationAdapter.OvergroundStationAdapterOnClickHandler, OvergroundStationAsyncTaskInterface
{
    //Tag for the log messages
    private static final String TAG = OverStatListActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_over_station)
    RecyclerView mStationRecyclerView;

    private OvergroundStationAdapter overStatAdapter;
    private ArrayList<OvergroundStation> overStatArrayList = new ArrayList<>();
    private ArrayList<OvergroundStatus> overgroundStatusArrayList = new ArrayList<>();
    private static final String KEY_STAT_OVER_LIST = "stat_over_list";
    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;
    @BindView(R.id.empty_view_over_stat)
    TextView emptyStations;
    OvergroundStatus overground;
    public String overLineId;
    private String overModeName;
    private String overModeStatusDesc;
    private String overModeStatusReason;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_stat_list);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        overStatAdapter = new OvergroundStationAdapter(this, overStatArrayList, context);
        mStationRecyclerView.setAdapter(overStatAdapter);

        RecyclerView.LayoutManager mStationLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mStationRecyclerView.setLayoutManager(mStationLayoutManager);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (getIntent() != null && getIntent().getExtras() != null)
        {
            overground = getIntent().getExtras().getParcelable("OvergroundStatus");
//            overLineId = getIntent().getExtras().getString("OverLineId");
//            overModeName = getIntent().getExtras().getString("OverModeName");
//            overModeStatusDesc= getIntent().getExtras().getString("OverModeDesc");
//            overModeStatusReason = getIntent().getExtras().getString("OverModeReason");

//            if(overLineId != null && overModeName != null && overModeStatusDesc != null && overModeStatusReason != null)
//            {
            if(overground != null)
            {
             overLineId = Objects.requireNonNull(overground).getModeId();
               // overLineId= overground.getModeId();
            /*
             *  Starting the asyncTask so that stations load when the activity opens.
             *
               */
//                Timber.i(overground.getModeId(), "lineId: ");
           }
            overgroundStatusArrayList = getIntent().getParcelableArrayListExtra("overgroundStatusList");


            if (savedInstanceState == null)
            {
                OvergroundStationAsyncTask myOverStatTask = new OvergroundStationAsyncTask(this);
                myOverStatTask.execute(overLineId);

            } else
                {
                overStatArrayList = savedInstanceState.getParcelableArrayList(KEY_STAT_OVER_LIST);
                overStatAdapter.setStationList(overStatArrayList);
            }
        }

    }

    @Override
    public void returnOverStationData(ArrayList<OvergroundStation> simpleJsonOverStatData)
    {
        if (null != simpleJsonOverStatData)
        {
            overStatAdapter = new OvergroundStationAdapter(this, simpleJsonOverStatData, OverStatListActivity.this);
            overStatArrayList = simpleJsonOverStatData;
            mStationRecyclerView.setAdapter(overStatAdapter);
            overStatAdapter.setStationList(overStatArrayList);
        }
        else
        {
            Timber.e("Problem parsing overground stations JSON results" );
            emptyStations.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(OvergroundStation overgroundStation)
    {
        Intent intent = new Intent(OverStatListActivity.this, OverScheduleActivity.class);
        intent.putExtra("OvergroundStatus", overground);
        intent.putExtra("OvergroundStation", overgroundStation);
//        intent.putExtra("OverLineId", overLineId);
//        intent.putExtra("OverModeName", overModeName);
//        intent.putExtra("OverModeDesc", overModeStatusDesc);
//        intent.putExtra("OverModeReason", overModeStatusReason);
        intent.putParcelableArrayListExtra("overgroundStatusList", overgroundStatusArrayList);
        intent.putParcelableArrayListExtra("overgroundStationList", overStatArrayList);
        startActivity(intent);

        //log event when the user selects a station
        Bundle params = new Bundle();
        params.putParcelable("station_select", overgroundStation);
        mFirebaseAnalytics.logEvent("station_select",params);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (emptyStations.getVisibility() == View.VISIBLE)
        {
            outState.putBoolean("visible", true);
        } else {
            outState.putBoolean("visible", false);
        }

        outState.putParcelableArrayList(KEY_STAT_OVER_LIST, overStatArrayList);
        super.onSaveInstanceState(outState);
    }


}
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.OvergroundAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.pojo.Overground;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.OvergroundAdapter;
import capstone.my.annin.londontubeschedule.scrollbehavior.DisableSwipeBehavior;
import capstone.my.annin.londontubeschedule.settings.SettingsActivity;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;
import timber.log.Timber;

public class OvergroundLineFragment extends Fragment implements OvergroundAdapter.OvergroundAdapterOnClickHandler, OvergroundAsyncTaskInterface
    {
    public OvergroundLineFragment()
        {
            // Required empty public constructor
        }

        @BindView(R.id.recyclerview_overground)
        RecyclerView mOvergroundRecyclerView;
        private OvergroundAdapter overgroundAdapter;
        private ArrayList<Overground> overgroundArrayList = new ArrayList<>();
        private Context context;
        private static final String KEY_OVERGROUND_LIST = "overground_list";
        private static final String KEY_SORT_ORDER = "sort_order";
        private String selectedSortOrder = "overground_list";
        private static final String SORT_BY_OVERGROUND = "overground_sort";
        private static final String KEY_LOADING_INDICATOR = "loading_indicator";
        CoordinatorLayout mCoordinatorLayout;

        @BindView(R.id.pb_loading_indicator)
        ProgressBar mLoadingIndicator;

        private int mPosition = RecyclerView.NO_POSITION;
        private FirebaseAnalytics mFirebaseAnalytics;

        private static final String SNACKBAR_STATE = "snackbar_state";
        private boolean isSnackbarShowing = false;


//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment TubeLineFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static TubeLineFragment newInstance(String param1, String param2)
//    {
//        TubeLineFragment fragment = new TubeLineFragment();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null)
//        {
//
//        }
//    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
        {
            // Inflate the layout for this fragment
            final View view = inflater.inflate(R.layout.fragment_overground_line, container, false);

            context = getContext();

            // Bind the views
            ButterKnife.bind(this, view);

            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

            mCoordinatorLayout = view.findViewById(R.id.coordinatorLayout);

            overgroundAdapter = new OvergroundAdapter(this, overgroundArrayList, context);

            mOvergroundRecyclerView.setAdapter(overgroundAdapter);

            RecyclerView.LayoutManager mLineLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mOvergroundRecyclerView.setLayoutManager(mLineLayoutManager);


            /*
             *  Starting the asyncTask so that lines load upon launching the app.
             */
            if (savedInstanceState == null)
            {
                if (isNetworkStatusAvailable(getContext()))
                {

                    OvergroundAsyncTask myOvergroundTask = new OvergroundAsyncTask(this);
                    myOvergroundTask.execute(NetworkUtils.buildOvergroundStatusUrl());
                } else
                {
                    Snackbar
                            .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.snackbar_retry, new OvergroundLineFragment.MyClickListener())
                            .setBehavior(new DisableSwipeBehavior())
                            .show();
                    isSnackbarShowing = true;
                    showErrorMessage();
                }
            } else
            {
                selectedSortOrder = savedInstanceState.getString(KEY_SORT_ORDER, "overground_list");
                isSnackbarShowing = savedInstanceState.getBoolean(SNACKBAR_STATE);
                if (isSnackbarShowing)
                {
                    Snackbar
                            .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.snackbar_retry, new OvergroundLineFragment.MyClickListener())
                            .setBehavior(new DisableSwipeBehavior())
                            .show();
                }
                else {
                    overgroundArrayList = savedInstanceState.getParcelableArrayList(KEY_OVERGROUND_LIST);
                    overgroundAdapter.setOvergroundList(overgroundArrayList);

                }
                if (savedInstanceState != null)
                {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                }

            }

            return view;
        }
        public class MyClickListener implements View.OnClickListener
        {
            @Override
            public void onClick(View v)
            {
                //CP Provider Code Commented out
                // Run the AsyncTask in response to the click
                // if (selectedSortOrder == SORT_BY_FAVORITES)
                //          {


//              getSupportLoaderManager().initLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
//               mLineRecyclerView.setAdapter(favoritesAdapter);
                //   } else
                //   {

                if (isNetworkStatusAvailable(context))
                {
                    OvergroundAsyncTask myOvergroundTask = new OvergroundAsyncTask(OvergroundLineFragment.this::returnOvergroundData);
                    myOvergroundTask.execute();
                }
                else
                {
                    Snackbar
                            .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.snackbar_retry, new OvergroundLineFragment.MyClickListener())
                            .setBehavior(new DisableSwipeBehavior())
                            .show();
                    isSnackbarShowing = true;
                    showErrorMessage();
                }
            }
        }

        @Override
        public void returnOvergroundData(ArrayList<Overground> simpleJsonOvergroundData)
        {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (null != simpleJsonOvergroundData)
            {
                overgroundAdapter = new OvergroundAdapter(this, simpleJsonOvergroundData, getContext());
                overgroundArrayList = simpleJsonOvergroundData;
                mOvergroundRecyclerView.setAdapter(overgroundAdapter);
                overgroundAdapter.setOvergroundList(overgroundArrayList);
            } else
            {
                showErrorMessage();
                Timber.e("Problem parsing lines JSON results" );
            }
        }

        @Override
        public void onClick(Overground overground)
        {
            //log event when the user selects a line
            Bundle params = new Bundle();
            params.putParcelable("overground_select", overground);
            mFirebaseAnalytics.logEvent("overground_select",params);

        }

        //Display if there is no internet connection
        public void showErrorMessage()
        {
            Snackbar
                    .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.snackbar_retry, new OvergroundLineFragment.MyClickListener())
                    .setBehavior(new DisableSwipeBehavior())
                    .show();
            // mLineRecyclerView.setVisibility(View.VISIBLE);
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
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
        {
            inflater.inflate(R.menu.overground, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            OvergroundAsyncTask myOvergroundTask = new OvergroundAsyncTask(this);

            switch (item.getItemId())
            {
                case R.id.overground_list:
                    myOvergroundTask.execute();
                    selectedSortOrder = SORT_BY_OVERGROUND;
                    return true;

                case R.id.settings:
                    Intent intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
                    return true;

                default:
                    return super.onOptionsItemSelected(item);

            }}


        @Override
        public void onSaveInstanceState(Bundle outState)
        {
            outState.putString(KEY_SORT_ORDER, selectedSortOrder);
            outState.putParcelableArrayList(KEY_OVERGROUND_LIST, overgroundArrayList);
            outState.putBoolean(SNACKBAR_STATE, isSnackbarShowing);
            super.onSaveInstanceState(outState);
        }
    }
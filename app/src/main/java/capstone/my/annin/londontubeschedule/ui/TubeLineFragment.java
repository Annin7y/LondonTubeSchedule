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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.data.LineViewModel;
import capstone.my.annin.londontubeschedule.maps.StationMapActivity;
import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.FavoritesRoomAdapter;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.LineAdapter;
import capstone.my.annin.londontubeschedule.settings.SettingsActivity;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;
import timber.log.Timber;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link TubeLineFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TubeLineFragment extends BaseFragment implements LineAdapter.LineAdapterOnClickHandler, TubeLineAsyncTaskInterface
{
    public TubeLineFragment()
    {
        // Required empty public constructor
    }

    @BindView(R.id.recyclerview_tube)
    RecyclerView mLineRecyclerView;
    private LineAdapter lineAdapter;
    private ArrayList<Line> lineArrayList = new ArrayList<>();
    private Context context;
    private static final String KEY_LINES_LIST = "line_list";
    private static final String KEY_SORT_ORDER = "sort_order";
    private String selectedSortOrder = "line_list";
    private static final String SORT_BY_FAVORITES = "line_favorites";
    private static final String SORT_BY_LINES = "line_sort";
    private static final String KEY_LOADING_INDICATOR = "loading_indicator";
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    //private FavoritesAdapter favoritesAdapter;
    private FavoritesRoomAdapter favoritesRoomAdapter;
    private static final int FAVORITES_LOADER_ID = 0;
    private int mPosition = RecyclerView.NO_POSITION;
    private FirebaseAnalytics mFirebaseAnalytics;

    //ViewModel variable
    private LineViewModel mLineViewModel;
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
        final View view = inflater.inflate(R.layout.fragment_tube_line, container, false);

        context = getContext();

        // Bind the views
        ButterKnife.bind(this, view);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        mCoordinatorLayout = view.findViewById(R.id.coordinatorLayout);

        //favoritesAdapter = new FavoritesAdapter(this, context);
        favoritesRoomAdapter = new FavoritesRoomAdapter(this, context);
        mLineViewModel = ViewModelProviders.of(this).get(LineViewModel.class);

        lineAdapter = new LineAdapter(this, lineArrayList, context);

        mLineRecyclerView.setAdapter(lineAdapter);

        RecyclerView.LayoutManager mLineLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLineRecyclerView.setLayoutManager(mLineLayoutManager);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            //Disable swipe for items not in Favorites; code sample based on the first answer in this stackoverflow post:
            //https://stackoverflow.com/questions/30713121/disable-swipe-for-position-in-recyclerview-using-itemtouchhelper-simplecallback
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
            {
                if (!(viewHolder instanceof FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder))
                {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir)
            {
                int position = viewHolder.getAdapterPosition();
                mLineViewModel.delete(favoritesRoomAdapter.getMovieAt(position));
            }

        }).attachToRecyclerView(mLineRecyclerView);

        /*
         *  Starting the asyncTask so that lines load upon launching the app.
         */
        if (savedInstanceState == null)
        {
//            if (isNetworkStatusAvailable(requireContext()))
        //  {

              TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(this);
             myLineTask.execute(NetworkUtils.buildLineStatusUrl());
        //    showSnackbar();
          //  }
//            else
//                {
//                Snackbar
//                        .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.snackbar_retry, new TubeLineFragment.MyClickListener())
//                        .setBehavior(new DisableSwipeBehavior())
//                        .show();
//                isSnackbarShowing = true;
//                showErrorMessage();
   //         }
        } else
            {
            selectedSortOrder = savedInstanceState.getString(KEY_SORT_ORDER, "line_list");
//            isSnackbarShowing = savedInstanceState.getBoolean(SNACKBAR_STATE);
//            if (isSnackbarShowing)
//            {
//                Snackbar
//                        .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.snackbar_retry, new TubeLineFragment.MyClickListener())
//                        .setBehavior(new DisableSwipeBehavior())
//                        .show();
//            }
            if (selectedSortOrder == SORT_BY_FAVORITES)
            {
                mLineRecyclerView.setAdapter(favoritesRoomAdapter);
                mLineViewModel.loadAllLines().observe(getViewLifecycleOwner(), new Observer<List<Line>>()
                {
                    @Override
                    public void onChanged(@Nullable List<Line> line)
                    {
                        favoritesRoomAdapter.setLine(line);
                    }
                });

//                    getSupportLoaderManager().initLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
//                    mLineRecyclerView.setAdapter(favoritesAdapter);

            } else
                {
                lineArrayList = savedInstanceState.getParcelableArrayList(KEY_LINES_LIST);
                lineAdapter.setLineList(lineArrayList);

            }
            if (savedInstanceState != null)
            {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
            } else
                {
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }
            //  Log.v(LOG_TAG, "SORTORDER= " + selectedSortOrder);
            Timber.v(selectedSortOrder, "SORTORDER= ");
            // Log.i("list", linesArrayList.size() + "");
            Timber.i("list: " + lineArrayList.size());
        }

        return view;
    }
//    public class MyClickListener implements View.OnClickListener
//    {
//        @Override
//        public void onClick(View v)
//        {
            //CP Provider Code Commented out
            // Run the AsyncTask in response to the click
            // if (selectedSortOrder == SORT_BY_FAVORITES)
            //          {


//              getSupportLoaderManager().initLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
//               mLineRecyclerView.setAdapter(favoritesAdapter);
            //   } else
            //   {

//            if (isNetworkStatusAvailable(context))
//            {
//                TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(TubeLineFragment.this);
//                myLineTask.execute();
//            }
//            else
//            {
//                Snackbar
//                        .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.snackbar_retry, new TubeLineFragment.MyClickListener())
//                        .setBehavior(new DisableSwipeBehavior())
//                        .show();
//                isSnackbarShowing = true;
//                showErrorMessage();
//            }
//        }
//    }

    @Override
    public void returnLineData(ArrayList<Line> simpleJsonLineData)
    {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (null != simpleJsonLineData)
        {
            lineAdapter = new LineAdapter(this, simpleJsonLineData, getContext());
            lineArrayList = simpleJsonLineData;
            mLineRecyclerView.setAdapter(lineAdapter);
            lineAdapter.setLineList(lineArrayList);
        } else
        {
           // showErrorMessage();
            Timber.e("Problem parsing lines JSON results" );
        }
    }

    @Override
    public void onClick(Line line)
    {
        Intent intent = new Intent(getActivity(), StationListActivity.class);
        intent.putExtra("Line", line);
        intent.putParcelableArrayListExtra("lineList", lineArrayList);
        startActivity(intent);

        //log event when the user selects a line
        Bundle params = new Bundle();
        params.putParcelable("line_select", line);
        mFirebaseAnalytics.logEvent("line_select",params);

    }

    public void showSnackbar()
    {
        TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(this);
        myLineTask.execute(NetworkUtils.buildLineStatusUrl());
    }

//    @Override
//    public void onAttach(@NonNull Context context)
//    {
//        super.onAttach(context);
//        try
//        {
//            ((MainActivity) getActivity()).setOnDataListener(this);
//        } catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }

    //Display if there is no internet connection
//    public void showErrorMessage()
//    {
//        Snackbar
//                .make(mCoordinatorLayout, R.string.snackbar_internet, Snackbar.LENGTH_INDEFINITE)
//                .setAction(R.string.snackbar_retry, new TubeLineFragment.MyClickListener())
//                .setBehavior(new DisableSwipeBehavior())
//                .show();
//        // mLineRecyclerView.setVisibility(View.VISIBLE);
//        mLoadingIndicator.setVisibility(View.VISIBLE);
//    }

//    public static boolean isNetworkStatusAvailable(Context context)
//    {
//        ConnectivityManager cm =
//                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        return activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting();
//    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs)
//    {
//        return new AsyncTaskLoader<Cursor>(this)
//        {
//
//            // Initialize a Cursor, this will hold all the task data
//            Cursor mFavoritesData = null;
//
//            // onStartLoading() is called when a loader first starts loading data
//            @Override
//            protected void onStartLoading()
//            {
//                if (mFavoritesData != null)
//                {
//                    // Delivers any previously loaded data immediately
//                    deliverResult(mFavoritesData);
//                }
//                else
//                {
//                    // Force a new load
//                    forceLoad();
//                }
//            }
//
//            // loadInBackground() performs asynchronous loading of data
//            @Override
//            public Cursor loadInBackground()
//            {
//                // Will implement to load data
//
//                // Query and load all task data in the background; sort by priority
//                // [Hint] use a try/catch block to catch any errors in loading data
//                try
//                {
//                    return getContentResolver().query(TubeLineContract.TubeLineEntry.CONTENT_URI,
//                            null,
//                            null,
//                            null,
//                             TubeLineContract.TubeLineEntry.COLUMN_LINES_ID);
//                }
//                catch (Exception e)
//                {
//                    Log.e(LOG_TAG, "Failed to asynchronously load data.");
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//
//            // deliverResult sends the result of the load, a Cursor, to the registered listener
//            public void deliverResult(Cursor data)
//            {
//                mFavoritesData = data;
//                super.deliverResult(data);
//            }
//        };
//    }
//
//    /**
//     * Called when a previously created loader has finished its load.
//     *
//     * @param loader The Loader that has finished.
//     * @param data   The data generated by the Loader.
//     */
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
//    {
//        favoritesAdapter.swapCursor(data);
//        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
//        mLineRecyclerView.smoothScrollToPosition(mPosition);
//    }
//
//    /**
//     * Called when a previously created loader is being reset, and thus
//     * making its data unavailable.
//     * onLoaderReset removes any references this activity had to the loader's data.
//     *
//     * @param loader The Loader that is being reset.
//     */
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader)
//    {
//        favoritesAdapter.swapCursor(null);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.tube, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        TubeLineAsyncTask myLineTask = new TubeLineAsyncTask(this);

        switch (item.getItemId())
        {
            case R.id.most_frequented_lines:

                favoritesRoomAdapter = new FavoritesRoomAdapter(this, getContext());
                mLineRecyclerView.setAdapter(favoritesRoomAdapter);
                mLineViewModel.loadAllLines().observe(this, new Observer<List<Line>>()
                {
                    @Override
                    public void onChanged(@Nullable List<Line> line)
                    {
                        favoritesRoomAdapter.setLine(line);
                    }
                });
                selectedSortOrder = SORT_BY_FAVORITES;
//                getSupportLoaderManager().restartLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
//                favoritesAdapter = new FavoritesAdapter(this, MainActivity.this);
//                mLineRecyclerView.setAdapter(favoritesAdapter);
//                selectedSortOrder = SORT_BY_FAVORITES;
                return true;

            case R.id.line_list:
                myLineTask.execute();
                selectedSortOrder = SORT_BY_LINES;
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
        outState.putParcelableArrayList(KEY_LINES_LIST, lineArrayList);
        outState.putBoolean(SNACKBAR_STATE, isSnackbarShowing);
        super.onSaveInstanceState(outState);
    }
}






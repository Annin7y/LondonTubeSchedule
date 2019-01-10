package capstone.my.annin.londontubeschedule.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.model.Lines;
import capstone.my.annin.londontubeschedule.model.Stations;
import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class TubeStationAsyncTask extends AsyncTask<String, Void, ArrayList<Stations>>
{
    private static final String TAG = TubeStationAsyncTask.class.getSimpleName();

    private TubeStationAsyncTaskInterface listener;

    public TubeStationAsyncTask(TubeStationAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Stations> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        String lineId = params[0];

        URL stationRequestUrl = NetworkUtils.buildStationUrl(lineId);

        try {
            String jsonStationResponse = NetworkUtils
                    .makeHttpStationRequest(stationRequestUrl);

            return JSONUtils.extractFeatureFromStationJson(jsonStationResponse);

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Stations> mStationList) {
        super.onPostExecute(mStationList);
        /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mStationList != null) {}
        listener.returnStationData(mStationList);

    }
}
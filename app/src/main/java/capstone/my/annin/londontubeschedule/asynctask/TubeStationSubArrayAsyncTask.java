package capstone.my.annin.londontubeschedule.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.Station;
import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class TubeStationSubArrayAsyncTask extends AsyncTask<String, Void,ArrayList<ArrayList<Station>> >
{
    private static final String TAG = TubeStationAsyncTask.class.getSimpleName();

    private TubeStationSubArrayAsyncTaskInterface listener;

    public TubeStationSubArrayAsyncTask(TubeStationSubArrayAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<ArrayList<Station>> doInBackground(String... params)
    {
        if (params.length == 0)
        {
            return null;
        }
        String lineId = params[0];

        URL stationRequestUrl = NetworkUtils.buildStationUrl(lineId);

        try
        {
            String jsonStationResponse = NetworkUtils
                    .makeHttpStationRequest(stationRequestUrl);

            return JSONUtils.extractFeatureFromStationSubArrayJson(jsonStationResponse);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<Station>> mStationSubList)
    {
        super.onPostExecute(mStationSubList);
        /*the if method is commented out because the error message will be displayed in the Main Activity if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mStationList != null) {}
        listener.returnStationSubData(mStationSubList);
    }
}


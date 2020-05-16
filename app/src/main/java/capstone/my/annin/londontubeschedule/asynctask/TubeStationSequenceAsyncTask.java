package capstone.my.annin.londontubeschedule.asynctask;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.net.URL;
import java.util.List;

import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class TubeStationSequenceAsyncTask extends AsyncTask<String, Void, JSONArray>
{

    private static final String TAG = TubeStationSequenceAsyncTask.class.getSimpleName();

    private TubeStationSequenceAsyncTaskInterface listener;

    public TubeStationSequenceAsyncTask(TubeStationSequenceAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected JSONArray doInBackground(String... params)
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

            return JSONUtils.extractRouteSequenceFromJson(jsonStationResponse);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONArray mLineStringsList)
    {
        super.onPostExecute(mLineStringsList);
        /*the if method is commented out because the error message will be displayed in the Main Activity if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mStationList != null) {}
        listener.returnStationSequenceData(mLineStringsList);
    }
}



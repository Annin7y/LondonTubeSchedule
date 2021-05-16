package capstone.my.annin.londontubeschedule.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.OvergroundStation;
import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class OvergroundStatAllAsyncTask extends AsyncTask<String, Void, ArrayList<OvergroundStation>>
{
    private static final String TAG = OvergroundStatAllAsyncTask.class.getSimpleName();

    private OvergroundStatAllAsyncTaskInterface listener;

    public OvergroundStatAllAsyncTask(OvergroundStatAllAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<OvergroundStation> doInBackground(String... params)
    {
        if (params.length == 0)
        {
            return null;
        }

        URL stationRequestUrl = NetworkUtils.buildOverStationAllUrl();

        try
        {
            String jsonOverStatResponse = NetworkUtils
                    .makeHttpOvergroundAllStationRequest(stationRequestUrl);

            return JSONUtils.extractFeatureFromOvergroundStationAllJson(jsonOverStatResponse);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<OvergroundStation> mOverStationList)
    {
        super.onPostExecute(mOverStationList);
         /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mLineList != null) {}
        listener.returnOverStationAllData(mOverStationList);
    }


}



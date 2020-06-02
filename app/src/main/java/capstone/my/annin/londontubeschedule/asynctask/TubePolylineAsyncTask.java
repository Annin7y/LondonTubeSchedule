package capstone.my.annin.londontubeschedule.asynctask;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.net.URL;

import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class TubePolylineAsyncTask extends AsyncTask<URL, Void, JSONArray>
{
    private static final String TAG = TubePolylineAsyncTask.class.getSimpleName();

    private TubePolylineAsyncTaskInterface listener;

    public TubePolylineAsyncTask(TubePolylineAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected JSONArray doInBackground(URL... params)
    {
        URL polylineRequestUrl = NetworkUtils.buildPolylineUrl();

        try
        {
            String jsonPolylineResponse = NetworkUtils
                    .makeHttpPolylineRequest(polylineRequestUrl);
            return jsonPolylineResponse;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONArray mPolylinesList)
    {
        super.onPostExecute(mPolylinesList);
         /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //if (mPolyinesList != null) {}
        listener.returnPolylineData(mPolylinesList);
    }



}

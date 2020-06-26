package capstone.my.annin.londontubeschedule.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;

import java.net.URL;

import capstone.my.annin.londontubeschedule.maps.ReadRawFile;


public class TubeRawJsonAsyncTask extends AsyncTask<String, Void, String>
{
    Context context;

    private static final String TAG = TubeRawJsonAsyncTask.class.getSimpleName();

    private TubeRawJsonAsyncTaskInterface listener;

    public TubeRawJsonAsyncTask(TubeRawJsonAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params)
    {
        if (params.length == 0)
        {
            return null;
        }
        String lineId = params[0];

        try {
            String rawGeoJson = ReadRawFile.readRawJson(context, lineId);
            return rawGeoJson;

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(String rawGeoJson)
    {
        super.onPostExecute(rawGeoJson);
         /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        listener.returnRawJsonData(rawGeoJson);
    }



}

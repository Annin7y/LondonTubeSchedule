package capstone.my.annin.londontubeschedule.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.Lines;
import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class TubeLineAsyncTask extends AsyncTask<URL, Void, ArrayList<Lines>>
{
    private static final String TAG = TubeLineAsyncTask.class.getSimpleName();

    private TubeLineAsyncTaskInterface listener;

    public TubeLineAsyncTask(TubeLineAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Lines> doInBackground(URL... params)
    {
        URL lineRequestUrl = NetworkUtils.buildLineUrl();

        try
        {
            String jsonLineResponse = NetworkUtils
                    .makeHttpLineRequest(lineRequestUrl);

            return JSONUtils.extractFeatureFromLineJson(jsonLineResponse);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

   @Override
    protected void onPostExecute(ArrayList<Lines> mLineList)
    {
        super.onPostExecute(mLineList);
         /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mLineList != null) {}
        listener.returnLineData(mLineList);
    }
}


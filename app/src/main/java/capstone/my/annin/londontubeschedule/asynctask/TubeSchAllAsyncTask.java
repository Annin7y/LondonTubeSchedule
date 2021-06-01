package capstone.my.annin.londontubeschedule.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.OvergroundSchedule;
import capstone.my.annin.londontubeschedule.pojo.Schedule;
import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class TubeSchAllAsyncTask extends AsyncTask<URL, Void, ArrayList<Schedule>>
{
    private static final String TAG = TubeSchAllAsyncTask.class.getSimpleName();

    private TubeSchAllAsyncTaskInterface listener;

    public TubeSchAllAsyncTask(TubeSchAllAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Schedule> doInBackground(URL... params)
    {
        URL tubeAllSchRequestUrl = NetworkUtils.buildTubeAllSchUrl();

        try
        {
            String jsonTubeAllSchResponse = NetworkUtils
                    .makeHttpTubeSchAllRequest(tubeAllSchRequestUrl);

            return JSONUtils.extractFeatureFromScheduleJson(jsonTubeAllSchResponse);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Schedule> mTubeScheduleAllList)
    {
        super.onPostExecute(mTubeScheduleAllList);
         /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mLineList != null) {}
        listener.returnTubeScheduleAllData(mTubeScheduleAllList);
    }
}






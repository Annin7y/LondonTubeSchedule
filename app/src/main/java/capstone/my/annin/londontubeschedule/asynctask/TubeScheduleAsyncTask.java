package capstone.my.annin.londontubeschedule.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.Schedule;
import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class TubeScheduleAsyncTask extends AsyncTask<String, Void, ArrayList<Schedule>>
{
    private static final String TAG = TubeScheduleAsyncTask.class.getSimpleName();

    private TubeScheduleAsyncTaskInterface listener;

    public TubeScheduleAsyncTask(TubeScheduleAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Schedule> doInBackground(String... params)
    {
        if (params.length == 0)
        {
            return null;
        }
        String lineId = params[0];
        String stationId = params[1];

        URL scheduleRequestUrl = NetworkUtils.buildScheduleUrl(lineId, stationId);

        try
        {
            String jsonScheduleResponse = NetworkUtils
                    .makeHttpScheduleRequest(scheduleRequestUrl);

            return JSONUtils.extractFeatureFromScheduleJson(jsonScheduleResponse);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Schedule> mScheduleList)
    {
        super.onPostExecute(mScheduleList);
        /*the if method is commented out because the error message will be displayed in the Main Activity if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mScheduleList != null) {}
        listener.returnScheduleData(mScheduleList);
    }
}

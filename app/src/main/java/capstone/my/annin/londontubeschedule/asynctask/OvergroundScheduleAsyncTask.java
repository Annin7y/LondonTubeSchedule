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

package capstone.my.annin.londontubeschedule.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.OvergroundSchedule;
import capstone.my.annin.londontubeschedule.pojo.Schedule;
import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class OvergroundScheduleAsyncTask extends AsyncTask<String, Void, ArrayList<OvergroundSchedule>>
{
    private static final String TAG = OvergroundScheduleAsyncTask.class.getSimpleName();

    private OvergroundScheduleAsyncTaskInterface listener;

    public OvergroundScheduleAsyncTask(OvergroundScheduleAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<OvergroundSchedule> doInBackground(String... params)
    {
        if (params.length == 0)
        {
            return null;
        }
        String overlineId = params[0];
        String statOverId = params[1];

        URL scheduleRequestUrl = NetworkUtils.buildOverSchUrl(overlineId, statOverId);

        try
        {
            String jsonScheduleResponse = NetworkUtils
                    .makeHttpOvergroundScheduleRequest(scheduleRequestUrl);

            return JSONUtils.extractFeatureFromOverSchJson(jsonScheduleResponse);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<OvergroundSchedule> mOverScheduleList)
    {
        super.onPostExecute(mOverScheduleList);
        /*the if method is commented out because the error message will be displayed in the Main Activity if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mOverScheduleList != null) {}
        listener.returnOverScheduleData(mOverScheduleList);
    }

}

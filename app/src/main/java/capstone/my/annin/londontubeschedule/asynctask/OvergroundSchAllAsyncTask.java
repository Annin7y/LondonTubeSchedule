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
import capstone.my.annin.londontubeschedule.pojo.OvergroundStatus;
import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class OvergroundSchAllAsyncTask extends AsyncTask<URL, Void, ArrayList<OvergroundSchedule>>
{
    private static final String TAG = OvergroundSchAllAsyncTask.class.getSimpleName();

    private OvergroundSchAllAsyncTaskInterface listener;

    public OvergroundSchAllAsyncTask(OvergroundSchAllAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<OvergroundSchedule> doInBackground(URL... params)
    {
        URL overgroundAllRequestUrl = NetworkUtils.buildOverSchArrivalUrl();

        try
        {
            String jsonLineResponse = NetworkUtils
                    .makeHttpOvergroundAllScheduleRequest(overgroundAllRequestUrl);

            return JSONUtils.extractFeatureFromOverSchJson(jsonLineResponse);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<OvergroundSchedule> mOverScheduleAllList)
    {
        super.onPostExecute(mOverScheduleAllList);
         /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mLineList != null) {}
        listener.returnOverScheduleAllData(mOverScheduleAllList);
    }
}





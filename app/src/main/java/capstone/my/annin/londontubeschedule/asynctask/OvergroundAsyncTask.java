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

import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.pojo.Overground;
import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

public class OvergroundAsyncTask extends AsyncTask<URL, Void, ArrayList<Overground>>
{
    private static final String TAG = OvergroundAsyncTask.class.getSimpleName();

    private OvergroundAsyncTaskInterface listener;

    public OvergroundAsyncTask(OvergroundAsyncTaskInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Overground> doInBackground(URL... params)
    {
        URL overgroundRequestUrl = NetworkUtils.buildOvergroundStatusUrl();

        try
        {
            String jsonLineResponse = NetworkUtils
                    .makeHttpOvergroundRequest(overgroundRequestUrl);

            return JSONUtils.extractFeatureFromOvergroundStatusJson(jsonLineResponse);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Overground> mOvergroundList)
    {
        super.onPostExecute(mOvergroundList);
         /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
        //   if (mLineList != null) {}
        listener.returnOvergroundData(mOvergroundList);
    }
}



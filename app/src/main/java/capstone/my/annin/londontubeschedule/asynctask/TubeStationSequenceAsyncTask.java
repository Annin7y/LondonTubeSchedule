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

import org.json.JSONArray;

import java.net.URL;

import capstone.my.annin.londontubeschedule.utils.JSONUtils;
import capstone.my.annin.londontubeschedule.utils.NetworkUtils;

//public class TubeStationSequenceAsyncTask extends AsyncTask<String, Void, JSONArray>
//{
//
//    private static final String TAG = TubeStationSequenceAsyncTask.class.getSimpleName();
//
//    private TubeStationSequenceAsyncTaskInterface listener;
//
//    public TubeStationSequenceAsyncTask(TubeStationSequenceAsyncTaskInterface listener)
//    {
//        this.listener = listener;
//    }
//
//    @Override
//    protected void onPreExecute()
//    {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected JSONArray doInBackground(String... params)
//    {
//        if (params.length == 0)
//        {
//            return null;
//        }
//        String lineId = params[0];
//
//        URL stationRequestUrl = NetworkUtils.buildStationUrl(lineId);
//
//        try
//        {
//            String jsonStationSequenceResponse = NetworkUtils
//                    .makeHttpStationRequest(stationRequestUrl);
//
//            return JSONUtils.extractRouteSequenceFromJson(jsonStationSequenceResponse);
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    protected void onPostExecute(JSONArray mLineStringsList)
//    {
//        super.onPostExecute(mLineStringsList);
//        /*the if method is commented out because the error message will be displayed in the Main Activity if there is no internet connection
//        the if statement is included in the returnData method in the Main Activity
//        */
//        //   if (mStationList != null) {}
//        listener.returnStationSequenceData(mLineStringsList);
//    }
//



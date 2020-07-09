package capstone.my.annin.londontubeschedule.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import capstone.my.annin.londontubeschedule.maps.ReadRawFile;

public class TubeGeoJsonAllLinesAsyncTask extends AsyncTask<String, Void, String>
    {
        //private static final String TAG = capstone.my.annin.londontubeschedule.asynctask.TubeRawJsonAsyncTask.class.getSimpleName();
        private AllLinesAsyncTaskInterface listener;
        private WeakReference<Context> contextRef2;


        public TubeGeoJsonAllLinesAsyncTask(AllLinesAsyncTaskInterface listener, Context context)
        {
            this.listener = listener;
            contextRef2 = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                String allGeoJson = ReadRawFile.readFromFile(contextRef2);
                return allGeoJson;

            } catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }

        }


        @Override
        protected void onPostExecute(String allGeoJson)
        {
            Context context = contextRef2.get();
            if (context != null)
            {
            /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
                listener.returnAllLinesJsonData(allGeoJson);
            }
        }
    }


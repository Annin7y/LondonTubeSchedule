package capstone.my.annin.londontubeschedule.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;

import java.lang.ref.WeakReference;
import java.net.URL;

import capstone.my.annin.londontubeschedule.maps.ReadRawFile;


public class TubeRawJsonAsyncTask extends AsyncTask<String, Void, String>
{
    private static final String TAG = TubeRawJsonAsyncTask.class.getSimpleName();
    private TubeRawJsonAsyncTaskInterface listener;
    private WeakReference<Context> contextRef;

    public TubeRawJsonAsyncTask(TubeRawJsonAsyncTaskInterface listener, Context context)
    {
        this.listener = listener;
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        String lineId = params[0];

            try {

                String rawGeoJson = ReadRawFile.readJsonFromAssets(lineId, contextRef);
                    return rawGeoJson;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

    }


    @Override
    protected void onPostExecute(String rawGeoJson) {
        Context context = contextRef.get();
        if (context != null) {
            super.onPostExecute(rawGeoJson);
         /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
            listener.returnRawJsonData(rawGeoJson);
        }
    }
}

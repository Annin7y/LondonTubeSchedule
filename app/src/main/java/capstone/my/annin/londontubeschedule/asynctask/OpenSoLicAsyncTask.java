package capstone.my.annin.londontubeschedule.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import capstone.my.annin.londontubeschedule.settings.ReadOpenSoLicFile;
import capstone.my.annin.londontubeschedule.settings.ReadPrivacyPolicyFile;

public class OpenSoLicAsyncTask extends AsyncTask<String, Void, String>
{
    private OpenSoLicAsyncTaskInterface listener;
    private WeakReference<Context> contextRef2;

    public OpenSoLicAsyncTask(OpenSoLicAsyncTaskInterface listener, Context context)
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
            String openLicenseText = ReadOpenSoLicFile.readFromFile(contextRef2);
            return openLicenseText;

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    protected void onPostExecute(String openLicenseText)
    {
        Context context = contextRef2.get();
        if (context != null)
        {
            /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
            listener.returnOpenSoLicFileData(openLicenseText);
        }
    }
}



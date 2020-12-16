package capstone.my.annin.londontubeschedule.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import capstone.my.annin.londontubeschedule.settings.ReadPrivacyPolicyFile;

public class PrivacyPolicyAsyncTask extends AsyncTask<String, Void, String>
{
    private PrivacyPolicyAsyncTaskInterface listener;
    private WeakReference<Context> contextRef2;

    public PrivacyPolicyAsyncTask(PrivacyPolicyAsyncTaskInterface listener, Context context)
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
            String policyText = ReadPrivacyPolicyFile.readFromFile(contextRef2);
            return policyText;

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    protected void onPostExecute(String policyText)
    {
        Context context = contextRef2.get();
        if (context != null)
        {
            /*the if method is commented out because the error message will be displayed if there is no internet connection
        the if statement is included in the returnData method in the Main Activity
        */
            listener.returnPrivacyPolicyFileData(policyText);
        }
    }
}

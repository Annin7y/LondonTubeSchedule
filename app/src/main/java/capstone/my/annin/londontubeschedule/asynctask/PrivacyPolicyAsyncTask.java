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

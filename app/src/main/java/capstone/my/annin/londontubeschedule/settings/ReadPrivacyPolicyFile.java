package capstone.my.annin.londontubeschedule.settings;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ReadPrivacyPolicyFile
{
    public static String readFromFile(WeakReference<Context> contextRef2)
    {
        //Read a file from assets and set it to TextView based on the following code samples:
        //https://nbasercode.com/net/android-read-text-file-from-assets-folder-in-android-studio/
        //https://readyandroid.wordpress.com/read-json-or-txt-file-from-assets-folder/
        String textPolicy = "";
        try
        {
            InputStream inputStream = contextRef2.get().getAssets().open("settings/privacypolicy.txt");
            if (inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(receiveString + "\n\n");
                }
                inputStream.close();
                textPolicy = stringBuilder.toString();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return textPolicy;
    }


}

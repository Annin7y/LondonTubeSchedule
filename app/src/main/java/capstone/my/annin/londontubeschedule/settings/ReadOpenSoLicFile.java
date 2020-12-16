package capstone.my.annin.londontubeschedule.settings;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

public class ReadOpenSoLicFile
{
    public static String readFromFile(WeakReference<Context> contextRef2)
    {
        String openLicenses = "";
        try
        {
            InputStream inputStream =  contextRef2.get().getAssets().open("settings/opensourcelicenses.txt");
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
                openLicenses = stringBuilder.toString();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return openLicenses;
    }
    }


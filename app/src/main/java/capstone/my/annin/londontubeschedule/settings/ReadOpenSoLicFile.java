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


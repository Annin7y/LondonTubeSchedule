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

package capstone.my.annin.londontubeschedule.maps;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Scanner;

import timber.log.Timber;

public class ReadRawFile
{

//    public static String readJsonFromAssets(String lineId, WeakReference<Context> contextRef)
//    {
//
//        //Code based on the following code sample:
//        //https://readyandroid.wordpress.com/read-json-or-txt-file-from-assets-folder/
//        String ret = "";
//
//        try {
//            String fileName = "json/" + lineId + ".json";
//            InputStream ins = contextRef.get().getAssets().open(fileName);
//
//            if (ins != null)
//            {
//                InputStreamReader inputStreamReader = new InputStreamReader(ins);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//                while ((receiveString = bufferedReader.readLine()) != null)
//                {
//                    stringBuilder.append(receiveString);
//                }
//                ins.close();
//                ret = stringBuilder.toString();
//            }
//        } catch (FileNotFoundException e)
//        {
//            Timber.e(e, "Problem parsing raw JSON results");
//        }
//        catch (IOException e)
//        {
//            Timber.e(e, "Problem parsing raw JSON results");
//        }
//        return ret;
//
//    }
    public static ArrayList<String> readFromFile(WeakReference<Context> contextRef2)
    {
        ArrayList<String> list = new ArrayList<>();
        try {
            InputStream inputStream = contextRef2.get().getAssets().open("json/allLines.txt");

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Scanner pieces = new Scanner(inputStreamReader);
                pieces.useDelimiter("--");
                while(pieces.hasNext())
                {
                    list.add(pieces.next());
                }
                inputStream.close();

                    } catch (FileNotFoundException e)

        {
            Timber.e(e, "Problem parsing raw JSON results");
        }
        catch (IOException e)
        {
            Timber.e(e, "Problem parsing raw JSON results");
        }
        return list;
    }

}



//               BufferedReader reader=new BufferedReader(new InputStreamReader(ins));
//             String jsonString ="";
//               StringBuilder builder=new StringBuilder();
//               try { while ((jsonString = reader.readLine()) != null) {
//                           builder.append(jsonString).append("\n");
//                       }
//              } catch (IOException e) {
//                   e.printStackTrace();
//              }
//               return builder.toString();
//           }

         //   }




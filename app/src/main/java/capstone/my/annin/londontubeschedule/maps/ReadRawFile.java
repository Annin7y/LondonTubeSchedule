package capstone.my.annin.londontubeschedule.maps;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

import timber.log.Timber;

public class ReadRawFile
{

    public static String readJsonFromAssets(String lineId, WeakReference<Context> contextRef)
    {

        //Code based on the following code sample:
        //https://readyandroid.wordpress.com/read-json-or-txt-file-from-assets-folder/
        String ret = "";

        try {
            String fileName = "json/" + lineId + ".json";
            InputStream ins = contextRef.get().getAssets().open(fileName);

            if (ins != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(ins);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(receiveString);
                }
                ins.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e)
        {
            Timber.e(e, "Problem parsing raw JSON results");
        }
        catch (IOException e)
        {
            Timber.e(e, "Problem parsing raw JSON results");
        }
        return ret;

    }}



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




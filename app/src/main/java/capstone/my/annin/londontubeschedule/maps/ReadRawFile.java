package capstone.my.annin.londontubeschedule.maps;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadRawFile
{
  public static String readRawJson(Context context, String lineId)
  {
      InputStream ins = context.getResources().openRawResource(
                           context.getResources().getIdentifier(lineId,
                                            "raw", context.getPackageName()));

               BufferedReader reader=new BufferedReader(new InputStreamReader(ins));
             String jsonString ="";
               StringBuilder builder=new StringBuilder();
               try { while ((jsonString = reader.readLine()) != null) {
                           builder.append(jsonString).append("\n");
                       }
              } catch (IOException e) {
                   e.printStackTrace();
              }
               return builder.toString();
           }

            }




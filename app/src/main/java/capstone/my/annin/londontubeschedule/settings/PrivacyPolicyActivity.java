package capstone.my.annin.londontubeschedule.settings;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.AllLinesAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.asynctask.PrivacyPolicyAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.PrivacyPolicyAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.asynctask.TubeGeoJsonAllLinesAsyncTask;

public class PrivacyPolicyActivity extends AppCompatActivity implements PrivacyPolicyAsyncTaskInterface
{
    private Context context;
    @BindView(R.id.privacy_policy_txt_file)
    TextView privacyPolicyText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);
      //  privacyPolicyText.setText(readAssetsFile());

        PrivacyPolicyAsyncTask myAllTask = new PrivacyPolicyAsyncTask(this, getApplicationContext());
        myAllTask.execute();

    }

    @Override
    public void returnPrivacyPolicyFileData(String simplePolicyFileString)
    {
        privacyPolicyText.setText(simplePolicyFileString);
    }

//    private String readAssetsFile ()
//    {
//        //Read a file from assets and set it to TextView based on the following code samples:
//        //https://nbasercode.com/net/android-read-text-file-from-assets-folder-in-android-studio/
//        //https://readyandroid.wordpress.com/read-json-or-txt-file-from-assets-folder/
//        String textPolicy = "";
//        try
//        {
//            InputStream inputStream = getAssets().open("settings/privacypolicy.txt");
//            if (inputStream != null)
//            {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//                while ((receiveString = bufferedReader.readLine()) != null)
//                {
//                    stringBuilder.append(receiveString + "\n\n");
//                }
//                inputStream.close();
//                textPolicy = stringBuilder.toString();
//            }
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        return textPolicy;
//    }

}

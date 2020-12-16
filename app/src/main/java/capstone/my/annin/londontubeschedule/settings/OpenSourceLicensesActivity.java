package capstone.my.annin.londontubeschedule.settings;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.OpenSoLicAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.OpenSoLicAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.asynctask.PrivacyPolicyAsyncTask;

public class OpenSourceLicensesActivity extends AppCompatActivity implements OpenSoLicAsyncTaskInterface
{
    private Context context;
    @BindView(R.id.open_licenses_txt_file)
    TextView openLicensePolicyText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source_licenses);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);
        //openLicensePolicyText.setText(readAssetsFile());

        OpenSoLicAsyncTask myAllTask = new OpenSoLicAsyncTask(this, getApplicationContext());
        myAllTask.execute();
    }

    @Override
    public void returnOpenSoLicFileData(String simpleOpenSoLicFileString)
    {
        openLicensePolicyText.setText(simpleOpenSoLicFileString );
    }


//    private String readAssetsFile ()
//    {
//        //Read a file from assets and set it to TextView based on the following code samples:
//        //https://nbasercode.com/net/android-read-text-file-from-assets-folder-in-android-studio/
//        //https://readyandroid.wordpress.com/read-json-or-txt-file-from-assets-folder/
//        String openLicenses = "";
//        try
//        {
//            InputStream inputStream = getAssets().open("settings/opensourcelicenses.txt");
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
//                openLicenses = stringBuilder.toString();
//            }
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        return openLicenses;
//    }

}

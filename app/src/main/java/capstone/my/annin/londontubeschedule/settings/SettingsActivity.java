package capstone.my.annin.londontubeschedule.settings;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;

public class SettingsActivity extends AppCompatActivity {
    private Context context;
    @BindView(R.id.privacy_policy_txt_file)
    TextView privacyPolicyText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);
        privacyPolicyText.setText(readAssetsFile());
    }
        private String readAssetsFile ()
        {
            //Read a file from assets and set it to TextView based on the following code samples:
            //https://nbasercode.com/net/android-read-text-file-from-assets-folder-in-android-studio/
            //https://readyandroid.wordpress.com/read-json-or-txt-file-from-assets-folder/
            String textPolicy = "";
            try {
                InputStream inputStream = getAssets().open("json/privacypolicy.txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }
                    inputStream.close();
                    textPolicy = stringBuilder.toString();


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return textPolicy;
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.settings, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.privacy_policy:
                privacyPolicyText.setText(readAssetsFile());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

}
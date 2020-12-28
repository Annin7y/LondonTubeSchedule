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
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.MarkdownTextView;
import com.yydcdut.markdown.syntax.text.TextFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.PrivacyPolicyAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.PrivacyPolicyAsyncTaskInterface;

public class PrivacyPolicyActivity extends AppCompatActivity implements PrivacyPolicyAsyncTaskInterface
{
    private Context context;
    @BindView(R.id.privacy_policy_txt_file)
    TextView privacyPolicyText;
    MarkdownTextView markdownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);
      //  privacyPolicyText.setText(readAssetsFile());

        markdownTextView = (MarkdownTextView) findViewById(R.id.privacy_policy_txt_file);

        PrivacyPolicyAsyncTask myAllTask = new PrivacyPolicyAsyncTask(this, getApplicationContext());
        myAllTask.execute();

    }

    @Override
    public void returnPrivacyPolicyFileData(String simplePolicyFileString)
    {
        //privacyPolicyText.setText(simplePolicyFileString);
        MarkdownProcessor processor = new MarkdownProcessor(this);
        processor.factory(TextFactory.create());
        markdownTextView.setText(processor.parse(simplePolicyFileString));
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

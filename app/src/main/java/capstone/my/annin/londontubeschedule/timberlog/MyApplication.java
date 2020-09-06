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

package capstone.my.annin.londontubeschedule.timberlog;

import android.app.Application;

import capstone.my.annin.londontubeschedule.BuildConfig;
import timber.log.Timber;

public class MyApplication extends Application
{
    //Based on the code in these links:
    //https://www.youtube.com/watch?v=0BEkVaPlU9A&t=1s&list=LLC3tmBcY0VaQGiTyNWKN70g&index=2
    //https://www.androidhive.info/2018/11/android-implementing-logging-using-timber/

    @Override
    public void onCreate()
    {
        super.onCreate();

        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        } else
        {
            Timber.plant(new ReleaseTree());
        }
    }
}

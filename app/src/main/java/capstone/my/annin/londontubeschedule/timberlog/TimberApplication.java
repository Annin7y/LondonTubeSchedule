package capstone.my.annin.londontubeschedule.timberlog;

import android.app.Application;

import capstone.my.annin.londontubeschedule.BuildConfig;
import timber.log.Timber;

public class TimberApplication extends Application
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

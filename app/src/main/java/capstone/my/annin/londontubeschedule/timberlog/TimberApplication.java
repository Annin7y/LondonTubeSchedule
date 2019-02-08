package capstone.my.annin.londontubeschedule.timberlog;

import android.app.Application;

import timber.log.Timber;

public class TimberApplication extends Application
{
    //Based on the code in this YouTube video:
    //https://www.youtube.com/watch?v=0BEkVaPlU9A&t=1s&list=LLC3tmBcY0VaQGiTyNWKN70g&index=2

    @Override
    public void onCreate()
    {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}

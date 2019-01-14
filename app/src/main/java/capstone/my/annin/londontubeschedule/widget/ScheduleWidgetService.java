package capstone.my.annin.londontubeschedule.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ScheduleWidgetService extends RemoteViewsService
{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        return new ScheduleWidgetViewFactory(getApplicationContext());
    }
}




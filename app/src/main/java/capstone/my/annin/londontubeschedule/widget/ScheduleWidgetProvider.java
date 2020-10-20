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

package capstone.my.annin.londontubeschedule.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.maps.StationMapActivity;
import capstone.my.annin.londontubeschedule.ui.MainActivity;
import capstone.my.annin.londontubeschedule.ui.StationListActivity;
import capstone.my.annin.londontubeschedule.ui.StationScheduleActivity;
import timber.log.Timber;

public class ScheduleWidgetProvider extends AppWidgetProvider
{
    //The following code is based on the code in these links:
    //https://joshuadonlan.gitbooks.io/onramp-android/content/widgets/collection_widgets.html
    //http://www.vogella.com/tutorials/AndroidWidgets/article.html
    //https://medium.com/android-bits/android-widgets-ad3d166458d3

    public static final String ACTION_VIEW_DETAILS =
            "annin.my.android.ScheduleWidgetProvider.ACTION_VIEW_DETAILS";

    public static final String EXTRA_ITEM =
            "annin.my.android.ScheduleWidgetProvider.EXTRA_ITEM";

    public void setPendingIntentTemplate(int viewId, PendingIntent pendingIntentTemplate)
    {
    }

    /*
    This method is called once a new widget is created as well as every update interval.
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds)
    {
        for (int i = 0; i < appWidgetIds.length; i++)
        {
            int widgetId = appWidgetIds[i];

            //    Build the intent to call the service
            Intent intent = new Intent(context.getApplicationContext(),
                    ScheduleWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            //Log.d("onUpdate", "method working");
            Timber.d("method working");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.schedule_widget_provider);
            views.setRemoteAdapter(R.id.appwidget_list, intent);
            views.setEmptyView(R.id.appwidget_list, R.id.empty);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context) ;
            stackBuilder.addNextIntent(new Intent(context, MainActivity.class));
            Intent detailIntent = new Intent(context, StationScheduleActivity.class);
            stackBuilder.addNextIntent(detailIntent);


            PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent pIntent = PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.appwidget_list, pIntent);

            appWidgetManager.updateAppWidget(widgetId, views);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

//    @Override
//    public void onReceive(Context context, Intent intent)
//    {
//        //Code structure based on the code in this blog:
//        //http://android-er.blogspot.com/2010/10/update-widget-in-onreceive-method.html
//        super.onReceive(context, intent);
//
//        if (ACTION_VIEW_DETAILS.equals(intent.getAction()))
//        {
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), ScheduleWidgetProvider.class.getName());
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
//
//            onUpdate(context, appWidgetManager, appWidgetIds);
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);
//        }
//    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }
}






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

package capstone.my.annin.londontubeschedule.utils;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import capstone.my.annin.londontubeschedule.ui.StationScheduleActivity;

public class DateUtils  extends Application
{
    public String futureTime;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        StationScheduleActivity scheduleActivity = new StationScheduleActivity();
        futureTime = scheduleActivity.stationShareArrivalTime;
    }
}




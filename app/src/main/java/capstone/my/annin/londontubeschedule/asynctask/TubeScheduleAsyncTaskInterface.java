package capstone.my.annin.londontubeschedule.asynctask;

import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.Schedule;

public interface TubeScheduleAsyncTaskInterface
{
    void returnScheduleData( ArrayList<Schedule> simpleJsonScheduleData);
}

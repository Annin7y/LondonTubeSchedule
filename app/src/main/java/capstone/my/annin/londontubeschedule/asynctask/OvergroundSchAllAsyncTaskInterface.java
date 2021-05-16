package capstone.my.annin.londontubeschedule.asynctask;

import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.OvergroundSchedule;

public interface OvergroundSchAllAsyncTaskInterface
{
    void returnOverScheduleAllData(ArrayList<OvergroundSchedule> simpleJsonOverSchAllData);
}

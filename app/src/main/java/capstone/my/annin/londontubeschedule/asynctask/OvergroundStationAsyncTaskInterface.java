package capstone.my.annin.londontubeschedule.asynctask;

import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.OvergroundStation;

public interface OvergroundStationAsyncTaskInterface
{
    void returnOverStationData( ArrayList<OvergroundStation> simpleJsonOverStatData);
}

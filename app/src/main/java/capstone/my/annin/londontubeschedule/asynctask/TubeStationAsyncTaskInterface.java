package capstone.my.annin.londontubeschedule.asynctask;

import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.Stations;

public interface TubeStationAsyncTaskInterface
{
    void returnStationData( ArrayList<Stations> simpleJsonStationData);
}

package capstone.my.annin.londontubeschedule.asynctask;

import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.pojo.Station;

public interface TubeStationAsyncTaskInterface
{
    void returnStationData(ArrayList<Station> simpleJsonStationData);
}

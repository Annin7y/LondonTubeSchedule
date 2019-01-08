package capstone.my.annin.londontubeschedule.asynctask;

import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.model.Stations;

public interface TubeStationAsyncTaskInterface
{
    void returnStationData( ArrayList<Stations> simpleJsonStationData);

}

package capstone.my.annin.londontubeschedule.asynctask;

import java.util.ArrayList;
import java.util.List;

import capstone.my.annin.londontubeschedule.pojo.Station;

public interface TubeStationSequenceAsyncTaskInterface
{
    void returnStationSequenceData(List<String> simpleJsonStationData);
}

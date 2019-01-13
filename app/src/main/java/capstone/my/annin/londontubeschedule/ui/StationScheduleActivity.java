package capstone.my.annin.londontubeschedule.ui;

import android.content.Context;
import android.content.Intent;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.model.Stations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class StationScheduleActivity extends AppCompatActivity
{
    private static final String BASE_STATION_URL_SHARE = "https://api.tfl.gov.uk";

    RecyclerView mScheduleRecyclerView;

    Stations stations;
    public String stationId;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_schedule);
    }

//    public Intent createShareIntent()
//    {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, BASE_STATION_URL_SHARE  + stationKey);
//        return shareIntent;
//    }
}

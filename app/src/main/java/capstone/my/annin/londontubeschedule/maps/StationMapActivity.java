package capstone.my.annin.londontubeschedule.maps;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Stations;
import timber.log.Timber;

public class StationMapActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private static final String TAG = "StationMapActivity";
    Stations stations;
    public String stationId;
    public double latLocation;
    public double lonLocation;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_map);

        if (getIntent() != null && getIntent().getExtras() != null)
        {
            if (savedInstanceState == null)
            {
                stations = getIntent().getExtras().getParcelable("Stations");

                stationId = stations.getStationId();
                // Log.i("stationId: ", stations.getStationId());
                Timber.v(stations.getStationId(), "stationId: ");

                latLocation = stations.getLatLocation();
                Timber.v(String.valueOf(stations.getLatLocation()));

                lonLocation = stations.getLonLocation();
                Timber.v(String.valueOf(stations.getLonLocation()));

            }
        }
    }
    //Explicitly check the permissions
    private void getLocationPermission()
    {
        String [] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,};
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

    }
}


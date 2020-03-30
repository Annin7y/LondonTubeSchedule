package capstone.my.annin.londontubeschedule.maps;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import capstone.my.annin.londontubeschedule.R;

public class StationMapActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private static final String TAG = "StationMapActivity";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_map);
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


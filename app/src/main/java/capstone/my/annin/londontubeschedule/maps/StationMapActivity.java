package capstone.my.annin.londontubeschedule.maps;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.pojo.Station;
import timber.log.Timber;

public class StationMapActivity extends AppCompatActivity implements OnMapReadyCallback
{
    /*
    * Define constants for the London coordinates. Useful for positioning the map
     */
   // private static final double LONDON_LAT = 51.5074;
  //  private static final double LONDON_LON = 0.1278;

    private static final String TAG = "StationMapActivity";
    Station station;
    public String stationId;
    public double latLocation;
   public double lonLocation;
   private GoogleMap mMap;
    /**
     * An ArrayList of Station objects.
     * For each station we will place a marker on the map,
     * and then we will draw a line through all of them.
     */
    private ArrayList<Station> stationArrayList = new ArrayList<>();
    private ArrayList<Station> lineArrayList = new ArrayList<>();


    /**
     * Specify the zoom level (from 2.0 to 21.0)
     * Values below this range are set to 2.0, and values above it are set to 21.0.
     * Increase the value to zoom in.
     */

    private static final float ZOOM = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_map);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()

                .findFragmentById(R.id.map);

        if (mapFragment != null)
        {
            mapFragment.getMapAsync(this);
        }

        if (getIntent() != null && getIntent().getExtras() != null)
        {
            station = getIntent().getExtras().getParcelable("Station");

            stationId = station.getStationId();
                // Log.i("stationId: ", stations.getStationId());
                Timber.v(station.getStationId(), "stationId: ");

                latLocation = station.getLatLocation();
                Timber.v(String.valueOf(station.getLatLocation()));

                lonLocation = station.getLonLocation();
                Timber.v(String.valueOf(station.getLonLocation()));

            stationArrayList = getIntent().getParcelableArrayListExtra("stationList");
            lineArrayList = getIntent().getParcelableArrayListExtra("lineList");
            }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        /*
          Define the options for a Polyline,
          Useful to draw a line on the map, so we can configure the line color, etc.
          A polyline is a list of points, where line segments are drawn between consecutive points.

          https://developers.google.com/android/reference/com/google/android/gms/maps/model/Polyline
          https://developers.google.com/android/reference/com/google/android/gms/maps/model/PolylineOptions
         */

        //PolylineOptions polylineOptions = new PolylineOptions();

        /*
          Here we go through each station in the ArrayList.
          For each of them we will extract its coordinates and station name
         */

       // for (Station station : stationArrayList)
       // {

        // Station lat, lon and name
        // double lat = station.getLatLocation();
       // double lon = station.getLonLocation();

            String stationName = station.getStationName();

            // Create a LatLng with the coordinates of each station
           LatLng stationCoordinates = new LatLng(latLocation, lonLocation);


            // For each station, we are adding its coordinates to the PolyLineOptions.
           // polylineOptions.add(stationCoordinates);


            /*
              Now we are going to draw a marker, also for each station.
              The MarkerOptions allows us to customize the marker (colors, thickness, icon, etc).

              https://developers.google.com/android/reference/com/google/android/gms/maps/model/MarkerOptions
             */
            MarkerOptions markerOptions = new MarkerOptions()

                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_place_black_18dp))
                    .title(stationName)
                    .position(stationCoordinates);

            // Once the MarkerOptions is set up, we add the marker.
        // This will be run for each station in the ArrayList.

            googleMap.addMarker(markerOptions);

        // After the loop, and having already added all the coordinates to the PolylineOptions,

        // we draw the line on the map

      // googleMap.addPolyline(polylineOptions);



        /*
           Finally we move the camera to the position specified by the London coordinates:
          https://developers.google.com/android/reference/com/google/android/gms/maps/GoogleMap

          https://developers.google.com/android/reference/com/google/android/gms/maps/CameraUpdateFactory
         */

        LatLng londonCoordinates = new LatLng(latLocation, lonLocation);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(londonCoordinates, ZOOM));

        PolylineOptions polylineOptions = new PolylineOptions();

        for (Station station : stationArrayList)
        {
            // Station lat, lon and name
             double lat = station.getLatLocation();
            double lon = station.getLonLocation();

            // Create a LatLng with the coordinates of each station
            LatLng stationLineCoordinates = new LatLng(lat, lon);
            polylineOptions.add(stationLineCoordinates);
            googleMap.addPolyline(polylineOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(londonCoordinates, ZOOM));

      }
        }
        
    }



//    private static final String TAG = "StationMapActivity";
//    Stations stations;
//    public String stationId;
//    public double latLocation;
//    public double lonLocation;
//    private GoogleMap mMap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_station_map);
//
//        if (getIntent() != null && getIntent().getExtras() != null)
//        {
//            if (savedInstanceState == null)
//            {
//                stations = getIntent().getExtras().getParcelable("Stations");
//
//                stationId = stations.getStationId();
//                // Log.i("stationId: ", stations.getStationId());
//                Timber.v(stations.getStationId(), "stationId: ");
//
//                latLocation = stations.getLatLocation();
//                Timber.v(String.valueOf(stations.getLatLocation()));
//
//                lonLocation = stations.getLonLocation();
//                Timber.v(String.valueOf(stations.getLonLocation()));
//
//            }
//        }
//    }
//    //Explicitly check the permissions
//    private void getLocationPermission()
//    {
//        String [] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,};
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap)
//    {
//        mMap = googleMap;
//
//    }



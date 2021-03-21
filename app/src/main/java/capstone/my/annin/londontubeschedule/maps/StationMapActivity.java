/* Copyright 2020 Anastasia Annin

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package capstone.my.annin.londontubeschedule.maps;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.asynctask.AllLinesAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.asynctask.TubeGeoJsonAllLinesAsyncTask;
import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStation;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStatus;
import capstone.my.annin.londontubeschedule.pojo.Station;
import capstone.my.annin.londontubeschedule.ui.StationScheduleActivity;
import timber.log.Timber;

public class StationMapActivity extends AppCompatActivity implements OnMapReadyCallback, AllLinesAsyncTaskInterface
{
    private static final String TAG = "StationMapActivity";
    Station station;
    public String stationId;
    Line line;
    public String lineId;
    public double latLocation;
    public double lonLocation;
    OvergroundStatus overground;
    OvergroundStation overgroundStation;
    public String overLineId;
    public String statOverId;
    private GoogleMap mMap;
    public GeoJsonLayer layer;
    private final int FILL_GREEN = 0x66aad2a5;
    private final int STROKE_GREEN = 0x387e40;


    /**
     * An ArrayList of Station objects.
     * For each station we will place a marker on the map,
     * and then we will draw a line through all of them.
     */
    private ArrayList<Station> stationArrayList = new ArrayList<>();
    private ArrayList<Station> lineArrayList = new ArrayList<>();
    private ArrayList<ArrayList<Station>> stationSubArrayList = new ArrayList<>();
    private ArrayList<OvergroundStatus> overgroundStatusArrayList = new ArrayList<>();
    private ArrayList<OvergroundStation> overgroundStatArrayList = new ArrayList<>();
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
        if (GooglePlayServicesCheck.isGooglePlayServicesAvailable(this))
        {
            if (getIntent() != null && getIntent().getExtras() != null)
            {
                line = getIntent().getExtras().getParcelable("Line");
                if (line != null)
                {
                    lineId = line.getLineId();
                    Timber.v(line.getLineId(), "lineId: ");

                    // Log.i("lineId: ", line.getLineId());
                    //Timber.v(line.getLineId(), "lineId: ");
                    station = getIntent().getExtras().getParcelable("Station");
                    if (station != null)
                    {
                        stationId = station.getStationId();
                        // Log.i("stationId: ", stations.getStationId());
                        Timber.v(station.getStationId(), "stationId: ");

                        latLocation = station.getLatLocation();
                        Timber.v(String.valueOf(station.getLatLocation()));

                        lonLocation = station.getLonLocation();
                        Timber.v(String.valueOf(station.getLonLocation()));
                    }
                    stationArrayList = getIntent().getParcelableArrayListExtra("stationList");
                    lineArrayList = getIntent().getParcelableArrayListExtra("lineList");

//            latLocation = station.getLatLocation();
//            Timber.v(String.valueOf(station.getLatLocation()));
//
//            lonLocation = station.getLonLocation();
//            Timber.v(String.valueOf(station.getLonLocation()));


                    //        TubeStationSequenceAsyncTask mySequenceTask = new TubeStationSequenceAsyncTask(this);
//        mySequenceTask.execute(lineId);
//        TubeRawJsonAsyncTask myRawJsonTask = new TubeRawJsonAsyncTask(this, getApplicationContext());
//        myRawJsonTask.execute(lineId);
//
                    //  TubeGeoJsonAllLinesAsyncTask myAllTask = new TubeGeoJsonAllLinesAsyncTask(this, getApplicationContext());
                    //  myAllTask.execute();
                }
                overground = getIntent().getExtras().getParcelable("OvergroundStatus");
                if(overground != null)
                {
                    overLineId = overground.getModeId();
                    Timber.v(overground.getModeId(), "modeId: ");

                    overgroundStation = getIntent().getExtras().getParcelable("OvergroundStation");
                    if (overgroundStation != null)
                    {
                        statOverId = overgroundStation.getStationId();
                        // Log.i("stationId: ", stations.getStationId());
                        Timber.v(overgroundStation.getStationId(), "statOverId: ");

                        latLocation = overgroundStation.getLatLocation();
                        Timber.v(String.valueOf(overgroundStation.getLatLocation()));

                        lonLocation = overgroundStation.getLonLocation();
                        Timber.v(String.valueOf(overgroundStation.getLonLocation()));
                    }
                    overgroundStatArrayList = getIntent().getParcelableArrayListExtra("stationList");
                    overgroundStatusArrayList = getIntent().getParcelableArrayListExtra("overgroundStatusList");
                }
            }
            if (mapFragment != null)
            {
                mapFragment.getMapAsync(this);
            }


        } else
            {
            Toast.makeText(StationMapActivity.this, getString(R.string.play_services_unavailable), Toast.LENGTH_SHORT).show();
        }
    }

    //Explicitly check the permissions
    private void getLocationPermission()
    {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,};
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        //map zoom code based on the code below:
        //https://stackoverflow.com/questions/29868121/how-do-i-zoom-in-automatically-to-the-current-location-in-google-maps-api-for-an
        mMap = googleMap;
        double lat = 51.509865;
        double lng = -0.118092 ;
        LatLng cityCoordinates = new LatLng(lat, lng);
        float zoomLevel = 9.0f;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityCoordinates, zoomLevel));

//        if(mMap != null)
//        {

        if (station != null)
        {
            String stationName = station.getStationName();
            // Create a LatLng with the coordinates of each station
           LatLng stationCoordinates = new  LatLng(latLocation, lonLocation);

            /*
              Now we are going to draw a marker, also for each station.
              The MarkerOptions allows us to customize the marker (colors, thickness, icon, etc).
               https://developers.google.com/android/reference/com/google/android/gms/maps/model/MarkerOptions
             */
            MarkerOptions markerOptions = new MarkerOptions()

                    // .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_place_black_18dp))
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .title(stationName)
                    .position(stationCoordinates);

            //Code copied and pasted from https://www.androidhub4you.com/2013/07/google-map-version-2-integration-in_8530.html
//        CircleOptions circle = new CircleOptions();
//        circle.center(stationCoordinates).fillColor(Color.LTGRAY).radius(50);
//        googleMap.addCircle(circle);

            // Once the MarkerOptions is set up, we add the marker.
            // This will be run for each station in the ArrayList.
            googleMap.addMarker(markerOptions);

        /*
         Finally we move the camera to the position specified by the coordinates:
           https://developers.google.com/android/reference/com/google/android/gms/maps/GoogleMap
           https://developers.google.com/android/reference/com/google/android/gms/maps/CameraUpdateFactory
         */

            //LatLng londonCoordinates = new LatLng(latLocation, lonLocation);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stationCoordinates, ZOOM));

            //Polyline code commented out; geoJson used to draw route lines
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
            // For each station, we are adding its coordinates to the PolyLineOptions.
            // polylineOptions.add(stationCoordinates);
            // for (Station station : stationArrayList)
            // {

            // Station lat, lon and name
            // double lat = station.getLatLocation();
            // double lon = station.getLonLocation();
//        PolylineOptions polylineOptions = new PolylineOptions()
//                .color(Color.GREEN)
//                .width(5);
//        for (Station station : stationArrayList) {
//            // Station lat, lon and name
//            double lat = station.getLatLocation();
//            double lon = station.getLonLocation();
//
//            // Create a LatLng with the coordinates of each station
//            LatLng stationLineCoordinates = new LatLng(lat, lon);
//            polylineOptions.add(stationLineCoordinates);
//        }
            // After the loop, and having already added all the coordinates to the PolylineOptions,
            // we draw the line on the map
//        googleMap.addPolyline(polylineOptions);
        }
        if (overgroundStation != null)
        {
            String statOverName = overgroundStation.getStationName();
            // Create a LatLng with the coordinates of each station
            LatLng stationCoordinates = new  LatLng(latLocation, lonLocation);
            MarkerOptions markerOptions = new MarkerOptions()

                    // .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_place_black_18dp))
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .title(statOverName)
                    .position(stationCoordinates);
            googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stationCoordinates, ZOOM));
        }

        //AsyncTask code from onCreate; AsyncTask now run after initializing the map
        TubeGeoJsonAllLinesAsyncTask myAllTask = new TubeGeoJsonAllLinesAsyncTask(this, getApplicationContext());
        myAllTask.execute();
        //   }
    }


//    @Override
//    public void returnRawJsonData(String simpleGeoJsonString)
//    {
//            try
//         {
//             String json = simpleGeoJsonString;
//             layer = new GeoJsonLayer(mMap, new JSONObject(json));
//                layer.addLayerToMap();
//                setPolygonGreen(layer);
//
//                for (GeoJsonFeature feature : layer.getFeatures())
//                {
//                    GeoJsonLineStringStyle stringStyle = new GeoJsonLineStringStyle();
//                    stringStyle.setColor(Color.GREEN);
//                    stringStyle.setWidth(4F);
//                    feature.setLineStringStyle(stringStyle);
//
//
//                }
//

//        {
//           e.printStackTrace();
//            }
//        }

    @Override
    public void returnAllLinesJsonData(ArrayList<String> simpleAllGeoJsonString)
    {
        if (simpleAllGeoJsonString.size() > 0)
        {
            ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(R.color.colorBakerloo, R.color.colorCentral, R.color.colorCircle,
                    R.color.colorDistrict, R.color.colorHammersmithCity, R.color.colorJubilee, R.color.colorMetropolitan, R.color.colorNorthern,
                    R.color.colorPiccadilly, R.color.colorVictoria, R.color.colorWaterloo, R.color.colorOverground, R.color.colorOverground,
                    R.color.colorOverground, R.color.colorOverground, R.color.colorOverground, R.color.colorOverground, R.color.colorOverground,
                    R.color.colorOverground, R.color.colorOverground, R.color.colorOverground, R.color.colorOverground, R.color.colorOverground,
                    R.color.colorOverground));

            for (int i = 0; i < simpleAllGeoJsonString.size(); i++)
            {
                try
                {

                    layer = new GeoJsonLayer(mMap, new JSONObject(simpleAllGeoJsonString.get(i)));
                    if (layer != null)
                    {
                        if (mMap != null)
                        {
                            layer.addLayerToMap();
                            setPolygonGreen(layer);
                            for (GeoJsonFeature feature : layer.getFeatures())
                            {
                                GeoJsonLineStringStyle stringStyle = new GeoJsonLineStringStyle();
                                stringStyle.setColor(ContextCompat.getColor(StationMapActivity.this, colors.get(i)));
                                stringStyle.setWidth(4F);
                                feature.setLineStringStyle(stringStyle);
                            }
                        }
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
//    @Override
//    public void returnStationSequenceData(JSONArray simpleJsonSequenceData) b
//    {
//        try
//        {
//            for (int i = 0; i < simpleJsonSequenceData.length(); i++) {
//                String json = "{\"type\":\"MultiLineString\",\"coordinates\":" + simpleJsonSequenceData.get(i).toString() + "}";
//                layer = new GeoJsonLayer(mMap, new JSONObject(json));
//                layer.addLayerToMap();
//               // setPolygonGreen(layer);
//
//                for (GeoJsonFeature feature : layer.getFeatures())
//                {
//                    GeoJsonLineStringStyle stringStyle = new GeoJsonLineStringStyle();
//                    stringStyle.setColor(Color.GREEN);
//                    stringStyle.setWidth(4F);
//                    feature.setLineStringStyle(stringStyle);
//                    GeoJsonPolygonStyle stringStyle = new GeoJsonPolygonStyle();
//                    stringStyle.setFillColor(Color.GREEN);
//                    stringStyle.setStrokeWidth(4F);
//                    feature.setPolygonStyle(stringStyle);
//
//                }
//
//
//            }
//            } catch (JSONException e)
//        {
//            e.printStackTrace();
//            }
//        }

    public void setPolygonGreen(GeoJsonLayer layer)
    {
        GeoJsonPolygonStyle polyStyle = layer.getDefaultPolygonStyle();
        polyStyle.setFillColor(Color.GREEN);
        polyStyle.setStrokeColor(Color.GREEN);
        polyStyle.setStrokeWidth(4f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.map, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.lineColorGuide:
                Intent intent = new Intent(this, LineColorGuideActivity.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
            finish();
            return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



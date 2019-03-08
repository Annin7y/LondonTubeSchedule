package capstone.my.annin.londontubeschedule.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import capstone.my.annin.londontubeschedule.BuildConfig;
import timber.log.Timber;

import static android.content.ContentValues.TAG;

public class NetworkUtils
{
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String APP_KEY = "app_key";
    private static final String APP_ID = "app_id";

    private static final String BASE_URL_LINES_LIST = "https://api.tfl.gov.uk/Line/Mode/tube";

    private static final String BASE_URL_STATIONS_LIST = "https://api.tfl.gov.uk/Line/";

    private static final String BASE_URL_SCHEDULE = "https://api.tfl.gov.uk/Line/";

    public NetworkUtils()
    {
    }

    public static URL buildLineUrl()
    {
        URL urlLineList = null;
        try
        {
            Uri lineListQueryUri = Uri.parse(BASE_URL_LINES_LIST).buildUpon()
                    .appendQueryParameter(APP_ID, BuildConfig.UNIFIED_LONDON_TRANSPORT_APP_ID)
                    .appendQueryParameter(APP_KEY, BuildConfig.UNIFIED_LONDON_TRANSPORT_APP_KEY)
                    .build();
            urlLineList = new URL(lineListQueryUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
      //  Log.v(TAG, "Built URIline " + urlLineList);
          Timber.v( "Built URIline " + urlLineList);
        return urlLineList;
    }

    public static URL buildStationUrl(String lineId)
    {
        URL urlStationsList = null;
        try
        {
            Uri stationsListQueryUri = Uri.parse(BASE_URL_STATIONS_LIST).buildUpon()
                    .appendPath(lineId)
                    .appendPath("Route")
                    .appendPath("Sequence")
                    .appendPath("inbound")
                    .appendQueryParameter("serviceTypes", "Regular,Night")
                    .appendQueryParameter("excludeCrowding", "false")
                    .appendQueryParameter(APP_ID, BuildConfig.UNIFIED_LONDON_TRANSPORT_APP_ID)
                    .appendQueryParameter(APP_KEY, BuildConfig.UNIFIED_LONDON_TRANSPORT_APP_KEY)
                    .build();
            urlStationsList = new URL(stationsListQueryUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URIStation " + urlStationsList);
        return urlStationsList;
    }

    public static URL buildScheduleUrl(String lineId, String stationId)
    {
        URL urlSchedule = null;
        try
        {
            Uri scheduleQueryUri = Uri.parse(BASE_URL_SCHEDULE).buildUpon()
                    .appendPath(lineId)
                    .appendPath("Arrivals")
                    .appendPath(stationId)
                    .appendQueryParameter(APP_KEY, BuildConfig.UNIFIED_LONDON_TRANSPORT_APP_KEY)
                    .appendQueryParameter(APP_ID, BuildConfig.UNIFIED_LONDON_TRANSPORT_APP_ID)
                    .build();
            urlSchedule = new URL(scheduleQueryUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URISchedule " + urlSchedule);
        return urlSchedule;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpLineRequest(URL url) throws IOException
    {
        String jsonLineResponse = "";
       // Log.i("URL: ", url.toString());
        Timber.i(url.toString(),"URL: " );

        // If the URL is null, then return early.
        if (url == null)
        {
            return jsonLineResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonLineResponse = readFromStream(inputStream);
            } else
                {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
           //Log.e(LOG_TAG, "Problem retrieving line list JSON results.", e);
            Timber.e(e,"Problem retrieving schedule JSON results." );
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (inputStream != null)
            {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonLineResponse;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpStationRequest(URL url) throws IOException
    {
        String jsonStationResponse = "";
       // Log.i("URL: ", url.toString());
        Timber.i(url.toString(),"URL: " );
        // If the URL is null, then return early.
        if (url == null)
        {
            return jsonStationResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonStationResponse = readFromStream(inputStream);
            } else
                {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
           // Log.e(LOG_TAG, "Problem retrieving station list JSON results.", e);
            Timber.e(e,"Problem retrieving schedule JSON results." );
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (inputStream != null)
            {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonStationResponse;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpScheduleRequest(URL url) throws IOException
    {
        String jsonScheduleResponse = "";
       // Log.i("URL: ", url.toString());
        Timber.i(url.toString(),"URL: " );
        // If the URL is null, then return early.
        if (url == null)
        {
            return jsonScheduleResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonScheduleResponse = readFromStream(inputStream);
            } else
                {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

            }
        }
        catch (IOException e)
        {
           // Log.e(LOG_TAG, "Problem retrieving schedule JSON results.", e);
            Timber.e(e,"Problem retrieving schedule JSON results." );
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (inputStream != null)
            {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonScheduleResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null)
            {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}

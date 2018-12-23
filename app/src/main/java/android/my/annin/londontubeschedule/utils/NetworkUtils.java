package android.my.annin.londontubeschedule.utils;

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

import static android.content.ContentValues.TAG;

public class NetworkUtils
{
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY = "api_key";
    private static final String APP_ID = "ap_id";

    private static final String BASE_URL_LINES_LIST = "https://api.tfl.gov.uk/swagger/ui/index.html?url=/swagger/docs/v1#!/Lines/Line_GetByMode";

    private static final String BASE_URL_STATIONS_LIST = "https://api.tfl.gov.uk/swagger/ui/index.html?url=/swagger/docs/v1#!/Lines/Line_RouteSequence";

    private static final String BASE_URL_SCHEDULE = "https://api.tfl.gov.uk/swagger/ui/index.html?url=/swagger/docs/v1#!/Lines/Line_Arrivals";

    public NetworkUtils()
    {
    }
    public static URL buildUrl()
    {
        URL urlTubeList = null;
        try
        {
            Uri tubeListQueryUri = Uri.parse(BASE_URL_LINES_LIST).buildUpon()
                    .build();
            urlTubeList = new URL(tubeListQueryUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + urlTubeList);
        return urlTubeList;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";
        Log.i("URL: ", url.toString());
        // If the URL is null, then return early.
        if (url == null)
        {
            return jsonResponse;
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
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving tube list JSON results.", e);
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
        return jsonResponse;
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

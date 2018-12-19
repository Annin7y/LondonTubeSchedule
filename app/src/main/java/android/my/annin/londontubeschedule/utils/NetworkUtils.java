package android.my.annin.londontubeschedule.utils;

public class NetworkUtils
{
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY = "api_key";
    private static final String APP_ID = "ap_id";

    private static final String BASE_URL_LINES_LIST = "https://api.tfl.gov.uk/swagger/ui/index.html?url=/swagger/docs/v1#!/Line/Line_GetByMode";

    private static final String BASE_URL_STATIONS_LIST = "https://api.tfl.gov.uk/swagger/ui/index.html?url=/swagger/docs/v1#!/Line/Line_RouteSequence";

    private static final String BASE_URL_SCHEDULE = "https://api.tfl.gov.uk/swagger/ui/index.html?url=/swagger/docs/v1#!/Line/Line_Arrivals";

    public NetworkUtils()
    {
    }


}

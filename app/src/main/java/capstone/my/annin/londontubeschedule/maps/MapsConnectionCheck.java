package capstone.my.annin.londontubeschedule.maps;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MapsConnectionCheck
{
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    //check if you have the correct API version
        public static boolean checkPlayServices(Context context)
        {
            Activity activity = (Activity) context;
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        if(result != ConnectionResult.SUCCESS)
        {
            if(googleAPI.isUserResolvableError(result))
            {
                googleAPI.getErrorDialog(activity,result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }
}

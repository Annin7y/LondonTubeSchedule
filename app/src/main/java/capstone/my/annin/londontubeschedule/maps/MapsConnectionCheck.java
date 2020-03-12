package capstone.my.annin.londontubeschedule.maps;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import capstone.my.annin.londontubeschedule.ui.StationScheduleActivity;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MapsConnectionCheck
{
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
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

package capstone.my.annin.londontubeschedule.maps;

import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import capstone.my.annin.londontubeschedule.ui.StationScheduleActivity;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MapsConnectionCheck
{
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }

}

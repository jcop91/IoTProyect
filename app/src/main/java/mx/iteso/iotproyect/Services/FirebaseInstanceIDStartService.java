package mx.iteso.iotproyect.Services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDStartService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    private String refreshedToken;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

    public static String getToken(){ return refreshedToken;}
}

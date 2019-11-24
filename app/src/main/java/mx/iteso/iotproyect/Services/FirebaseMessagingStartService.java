package mx.iteso.iotproyect.Services;

import android.app.Notification;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingStartService extends FirebaseMessagingService {
    String TAG ="Firebase Messages: ";
    int counter =0;

        @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       super.onMessageReceived(remoteMessage);

        // ...
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());



            String senderID = remoteMessage.getData().get("senderID");
            Log.d("SenderID", senderID);
            String localsenderID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.d("LocalSender", localsenderID);
            if(senderID.equals(localsenderID)){
            //if(senderID.equals(senderID)){
                //String title =  remoteMessage.getData().get("title");
                //String message = remoteMessage.getData().get("message");

                NotificationsHandler notificationsHandler = new NotificationsHandler(this);
                Notification.Builder nb = notificationsHandler.createNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),true);
                notificationsHandler.getManager().notify(++counter,nb.build());
            }

        }

        if (remoteMessage.getNotification() != null) {
            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


}

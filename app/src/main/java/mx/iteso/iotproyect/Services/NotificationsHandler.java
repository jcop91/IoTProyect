package mx.iteso.iotproyect.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.os.Build;

import mx.iteso.iotproyect.Activities.Main_Activity;
import mx.iteso.iotproyect.R;

public class NotificationsHandler extends ContextWrapper {

    private NotificationManager manager;
    public static final String CHANNEL_HIGH_ID="1";
    private final String CHANNEL_HIGH_NAME ="HIGH CHANNEL";
    public static final String CHANNEL_LOW_ID="2";
    private final String CHANNEL_LOW_NAME ="LOW CHANNEL";

    public NotificationsHandler(Context context){
        super(context);
        createChannels();
    }

    public NotificationManager getManager(){
        if(manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    private  void  createChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            NotificationChannel highChannel = new NotificationChannel(CHANNEL_HIGH_ID,CHANNEL_HIGH_NAME,NotificationManager.IMPORTANCE_HIGH);

            NotificationChannel lowChannel = new NotificationChannel(CHANNEL_LOW_ID,CHANNEL_LOW_NAME,NotificationManager.IMPORTANCE_LOW);

            highChannel.enableLights(true);
            highChannel.setShowBadge(true);
            highChannel.enableVibration(true);
            highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            highChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null);

            lowChannel.enableLights(true);
            lowChannel.setShowBadge(true);
            lowChannel.enableVibration(true);
            lowChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            lowChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null);

            getManager().createNotificationChannel(highChannel);
            getManager().createNotificationChannel(lowChannel);
        }
    }

    public Notification.Builder createNotification(String title, String message, boolean isHighImportance){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            if(isHighImportance){
                return this.createNotificationWithChannel(title, message, CHANNEL_HIGH_ID);
            }
            return this.createNotificationWithChannel(title, message, CHANNEL_LOW_ID);
        }
        return this.createNotificationWithoutChannel(title, message);
    }

    private Notification.Builder createNotificationWithChannel(String title,String message, String channelID){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Intent intent = new Intent(this, Main_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivities(this,0, new Intent[]{intent},PendingIntent.FLAG_CANCEL_CURRENT);

            Notification.Action action = new Notification.Action.Builder(
                    Icon.createWithResource(this,
                            android.R.drawable.ic_menu_info_details),
                    "Ver detalles",
                    pendingIntent
            ).build();

            return new Notification.Builder(getApplicationContext(), channelID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .addAction(action)
                    .setColor(getColor(R.color.cardview_dark_background))
                    .setSmallIcon(R.drawable.ic_tuppersens)
                    .setAutoCancel(true);
        }
        return  null;
    }

    private Notification.Builder createNotificationWithoutChannel(String title,String message){

        return  new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(message)
                .setColor(getColor(R.color.cardview_dark_background))
                .setSmallIcon(R.drawable.ic_tuppersens)
                .setAutoCancel(true);
    }
}

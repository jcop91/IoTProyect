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
import mx.iteso.iotproyect.Models.StringsClass;
import mx.iteso.iotproyect.R;

//TODO(Clase): Se encarga de estructurar las notificaciones en el dispositivo.
public class NotificationsHandler extends ContextWrapper {

    private NotificationManager manager;

    public NotificationsHandler(Context context){
        super(context);
        createChannels();
    }

    //TODO(Metodo): Crea la instancia de administrador de notificaciones.
    public NotificationManager getManager(){
        if(manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    //TODO(Metodo): Crea el canal para generar las notificaciones con su funcionalidades.
    private  void  createChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){

            NotificationChannel Channel = new NotificationChannel(
                    StringsClass.CHANNEL_ID,
                    StringsClass.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            //Se anexa los atributos que requiero en las notificaciones
            Channel.enableLights(true);
            Channel.setShowBadge(true);
            Channel.enableVibration(true);
            Channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            Channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null);

            getManager().createNotificationChannel(Channel);
        }
    }

    //TODO(Metodo): Se llama fuera de la clase para comenzar a generar notificaciones.
    public Notification.Builder createNotification(String title, String message, String id, String level){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            return this.createNotificationWithChannel(title, message, StringsClass.CHANNEL_ID, id , level);
        }
        return this.createNotificationWithoutChannel(title, message, id, level);
    }

    //TODO(Metodo): Se utiliza para asignar los datos con el esquema de la notificaciones
    // en versiones igual o mayor a la 26.
    private Notification.Builder createNotificationWithChannel(String title,String message, String channelID,String id, String level){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Intent intent = new Intent(this, Main_Activity.class);
            intent.putExtra("idTopper", id);
            intent.putExtra("level",level);
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

    //TODO(Metodo): Se utiliza para asignar los datos con el esquema de la notificaciones
    // en versiones menores a la 26.
    private Notification.Builder createNotificationWithoutChannel(String title,String message,String id, String level){
        Intent intent = new Intent(this, Main_Activity.class);
        intent.putExtra("idTopper", id);
        intent.putExtra("level",level);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivities(this,0, new Intent[]{intent},PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Action action = new Notification.Action.Builder(
                Icon.createWithResource(this,
                        android.R.drawable.ic_menu_info_details),
                "Ver detalles",
                pendingIntent
        ).build();

        return  new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(message)
                .addAction(action)
                .setColor(getColor(R.color.cardview_dark_background))
                .setSmallIcon(R.drawable.ic_tuppersens)
                .setAutoCancel(true);
    }
}

package mx.iteso.iotproyect.Services;

import android.app.Notification;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import mx.iteso.iotproyect.Models.StringsClass;

//TODO(Clase): Se encarga de recibir o enviar la mensajeria por Firebase Cloud Message.
public class FirebaseMessagingStartService extends FirebaseMessagingService {

    int counter =0;

    //TODO(Metodo): Manejador para recibir mensajes FCM.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       super.onMessageReceived(remoteMessage);

       //Imprime el consola el origen de la notificación
        Log.d(StringsClass.FCMTagTitleMessage, StringsClass.FCMTagFromMessage + remoteMessage.getFrom());

        //Valida que el mensaje no este vacio
        if (remoteMessage.getData().size() > 0) {
            Log.d(StringsClass.FCMTagTitleMessage, StringsClass.FCMTagMessage + remoteMessage.getData());

            //TODO(Creacion): Instancia la clase encargada de crear y visualizar las notificaciones.
            NotificationsHandler notificationsHandler = new NotificationsHandler(this);
            Notification.Builder nb = notificationsHandler.createNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody(),
                    remoteMessage.getData().get("id"),
                    remoteMessage.getData().get("level"));
            notificationsHandler.getManager().notify(++counter,nb.build());
        }
    }


}

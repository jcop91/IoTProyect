package mx.iteso.iotproyect.Services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import mx.iteso.iotproyect.Models.StringsClass;

//TODO(Clase): Se utiliza para instanciar el servicio de Firebase del dispositivo.
public class FirebaseInstanceIDStartService extends FirebaseInstanceIdService {

    private static String refreshedToken;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //Asigna valor e imprime en la consola
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(StringsClass.FCMTagTitleIDService, StringsClass.FCMTagTokenIDService + refreshedToken);

    }

    //TODO(Metodo): Se utiliza para obtener el token fuera de esta clase.
    public static String getToken(){ return refreshedToken;}
}

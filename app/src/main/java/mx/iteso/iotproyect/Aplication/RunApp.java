package mx.iteso.iotproyect.Aplication;

import android.app.Application;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import io.realm.Realm;
import io.realm.RealmResults;
import mx.iteso.iotproyect.Services.Tools;
import mx.iteso.iotproyect.Models.ToppersDB;
import mx.iteso.iotproyect.Models.UserDB;

//TODO(Clase Maestra): iniciar aplicacion.
public class RunApp extends Application {
    public static UserDB userDB;
    public static RealmResults<ToppersDB> toppers;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
        Tools.configRealms();
        Realm realm = Realm.getDefaultInstance();
        userDB = realm.where(UserDB.class).findFirst();
        toppers = realm.where(ToppersDB.class).findAll();
        realm.close();

    }
}
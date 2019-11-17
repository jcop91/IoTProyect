package mx.iteso.iotproyect.Aplication;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmResults;
import mx.iteso.iotproyect.Services.Tools;
import mx.iteso.iotproyect.Models.ToppersDB;
import mx.iteso.iotproyect.Models.UserDB;

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
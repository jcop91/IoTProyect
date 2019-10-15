package mx.iteso.iotproyect.Aplication;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import mx.iteso.iotproyect.Models.Tools;
import mx.iteso.iotproyect.Models.Toppers;
import mx.iteso.iotproyect.Models.User;

public class RunApp extends Application {
    public static boolean EmptyBD;
    public static User user;
    public static RealmResults<Toppers> toppers;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
        Tools.configRealms();
        Realm realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();
        toppers = realm.where(Toppers.class).findAll();
        EmptyBD = Tools.isEmptyDB(realm,User.class);
        realm.close();

    }
}
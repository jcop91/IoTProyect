package mx.iteso.iotproyect.Models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import mx.iteso.iotproyect.Adapter.AlertAdapter;

public class Tools {

    public static <T extends RealmObject> boolean isEmptyDB(Realm realm, Class<T> anyClass){
        RealmResults<T> result = realm.where(anyClass).findAll();

        return (result.size() > 0)? false: true;
    }
    public static void configRealms(){

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("iotTeam3.realm")
                .schemaVersion(42)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}

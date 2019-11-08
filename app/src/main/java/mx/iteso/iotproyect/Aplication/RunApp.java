package mx.iteso.iotproyect.Aplication;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmResults;
import mx.iteso.iotproyect.Models.Tools;
import mx.iteso.iotproyect.Models.Toppers;
import mx.iteso.iotproyect.Models.User;
import retrofit2.Retrofit;

public class RunApp extends Application {
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
        realm.close();

    }
}
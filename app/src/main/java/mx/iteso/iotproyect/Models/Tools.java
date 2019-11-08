package mx.iteso.iotproyect.Models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tools {
    private static final String Url = "http://tuppersens.us-west-2.elasticbeanstalk.com/api/servicesIoT/";
    private static final String localhost="148.201.214.14";//"192.168.1.6";//
    private static final String UrlPruebas = "http://"+localhost+"/IoT/api/servicesIoT/";

    public static <T extends RealmObject> boolean isEmptyDB(Realm realm, Class<T> anyClass){
        RealmResults<T> result = realm.where(anyClass).findAll();

        return (result.size() > 0)? false: true;
    }

    public static <T extends RealmObject> boolean existsTopper(Realm realm, Class<T> anyClass, String id){
        RealmResults<T> result = realm.where(anyClass).equalTo("id",id).findAll();

        return (result.size() > 0)? true: false;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isnotEmptyValid(String str){
        boolean isValid = false;

        if(!str.isEmpty()){
            isValid = true;
        }
        return isValid;
    }

    public static void configRealms(){

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("iotTeam3.realm")
                .schemaVersion(42)
                .build();

        Realm.setDefaultConfiguration(config);
    }

    public static Retrofit retrofit(){
        Retrofit instance = new Retrofit
                .Builder()
                .baseUrl(UrlPruebas)
                .addConverterFactory(GsonConverterFactory.create())
                //.baseUrl(Url)
                .build();
        return instance;
    }
}

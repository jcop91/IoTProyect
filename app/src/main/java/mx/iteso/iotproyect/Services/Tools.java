package mx.iteso.iotproyect.Services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import mx.iteso.iotproyect.Activities.Main_Activity;
import mx.iteso.iotproyect.Models.CustomCallBack;
import mx.iteso.iotproyect.Models.ToppersDB;
import mx.iteso.iotproyect.Models.UserDB;
import mx.iteso.iotproyect.Models.UserRequest;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tools {
    private static final String Url = "http://tuppersens.us-west-2.elasticbeanstalk.com/api/servicesIoT/";
    private static final String localhost="192.168.1.6";//"148.201.214.14";//
    private static final String UrlPruebas = "http://"+localhost+"/IoT/api/servicesiot/";

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

    public static void configRealms(){

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("iotTeam3.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(42)
                .build();

        Realm.setDefaultConfiguration(config);
    }
    private static Retrofit InstanceHttpMethods(){
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(UrlPruebas)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        return retrofit;
    }
    public static String getUserID(Realm realm){
        return realm.where(UserDB.class).findFirst().getId();
    }
    public static void sendNetworkRequestNewUser(final Context context, final UserRequest user, final Realm realm){

        AwsService service = InstanceHttpMethods().create(AwsService.class);
        final UserDB newUserDB = new UserDB(user.getFullname(),user.getEmail(),user.getSenderID());

        Call<UserRequest> call = service.createUser(user);
        call.enqueue(new CustomCallBack<UserRequest>(context){
            @Override
            public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context,"Error: "+ response.code(),Toast.LENGTH_LONG).show();
                }

                newUserDB.setId(response.body().getId());
                realm.beginTransaction();
                realm.copyToRealm(newUserDB);
                realm.commitTransaction();

                Intent intent = new Intent(context, Main_Activity.class);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<UserRequest> call, Throwable t) {
                Toast.makeText(context,"Error: "+ t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static  void sendNetworkRequestChangeTopperName(final Context context, final int type, final Realm realm, final ToppersDB toppersDB ){
        AwsService service = InstanceHttpMethods().create(AwsService.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", toppersDB.getId());
        jsonObject.addProperty("name",toppersDB.getName());
        jsonObject.addProperty("userId",getUserID(realm));

        Call<Void> call = service.changeTopperName(jsonObject);
        call.enqueue(new CustomCallBack<Void>(context){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context,"Error: "+ response.code(),Toast.LENGTH_LONG).show();
                }else{
                    if(type == 1){
                        realm.beginTransaction();
                        realm.copyToRealm(toppersDB);
                        realm.commitTransaction();
                    }
                    if(type == 2){
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(toppersDB);
                        realm.commitTransaction();
                    }
                    Intent intent = new Intent(context, Main_Activity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context,"Error: "+ t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static void dummyData(String data, Context context){
        AwsService service = Tools.InstanceHttpMethods().create(AwsService.class);
        Call<Void> call = service.dummydata(data);
        call.enqueue(new CustomCallBack<Void>(context) {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}


package mx.iteso.iotproyect.Models;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import mx.iteso.iotproyect.Activities.CaptureData;
import mx.iteso.iotproyect.Aplication.AwsService;
import retrofit2.Call;
import retrofit2.Callback;
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
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(42)
                .build();

        Realm.setDefaultConfiguration(config);
    }
    public static void sendNetworkRequestNewUser(final Context context, UserRequest user){
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(UrlPruebas)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        AwsService service = retrofit.create(AwsService.class);

       /* JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fullname", user.getFullname());
        jsonObject.addProperty("email",user.getEmail());
*/
        Call<UserRequest> call = service.createUser(user);

        call.enqueue(new Callback<UserRequest>() {
            @Override
            public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                if(!response.isSuccessful()){

                    Toast.makeText(context,"Successful: "+ response.code(),Toast.LENGTH_LONG).show();

                }
                Toast.makeText(context,"Successful: "+ response.body().getId(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<UserRequest> call, Throwable t) {
                Toast.makeText(context,"Error: "+ t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

/*    public static Retrofit retrofit(){
        Retrofit instance = new Retrofit
                .Builder()
                .baseUrl(UrlPruebas)
                .addConverterFactory(GsonConverterFactory.create())
                //.baseUrl(Url)
                .build();
        return instance;
    }*/
}

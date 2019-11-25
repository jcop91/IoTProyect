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
import mx.iteso.iotproyect.Models.StringsClass;
import mx.iteso.iotproyect.Models.ToppersDB;
import mx.iteso.iotproyect.Models.UserDB;
import mx.iteso.iotproyect.Models.UserRequest;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//TODO(Clase): Utilizada para crear funciones y validaciones de la aplicación.
public class Tools {

    //TODO(Metodo): Valida si alguna tabla de la base de datos esta vacia.
    public static <T extends RealmObject> boolean isEmptyTableDB(Realm realm, Class<T> anyClass){
        RealmResults<T> result = realm.where(anyClass).findAll();

        return (result.size() > 0)? false: true;
    }

    //TODO(Metodo): Valida si ya existe un topper por ID.
    public static <T extends RealmObject> boolean existsTopper(Realm realm, Class<T> anyClass, String id){
        RealmResults<T> result = realm.where(anyClass).equalTo(StringsClass.idField,id).findAll();

        return (result.size() > 0)? true: false;
    }

    //TODO(Metodo): Valida que el correo sea valido(Tenga el formato correcto).
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = StringsClass.emailFormat;
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //TODO(Metodo): Instancia la configuracion de la Base de datos para su uso.
    public static void configRealms(){

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(StringsClass.dbRealmName)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(42)
                .build();

        Realm.setDefaultConfiguration(config);
    }

    //TODO(Metodo): Instancia la ejecucion de metodos http a traves de retrofit.
    private static Retrofit InstanceHttpMethods(){
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(StringsClass.Url)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        return retrofit;
    }

    //TODO(Metodo): Obtiene el id del usuario.
    public static String getUserID(Realm realm){
        return realm.where(UserDB.class).findFirst().getId();
    }

    //TODO(Metodo): Realiza la peticion Post para registrar nuevo usuario.
    public static void sendNetworkRequestNewUser(final Context context, final UserRequest user, final Realm realm){

        //inicializa la peticion al metodo correspondiente
        AwsService service = InstanceHttpMethods().create(AwsService.class);
        final UserDB newUserDB = new UserDB(user.getFullname(),user.getEmail(),user.getSenderID());

        //Llama al metodo Call para escuchar la respuesta del WebService
        Call<UserRequest> call = service.createUser(user);
        call.enqueue(new CustomCallBack<UserRequest>(context){
            @Override
            public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(
                            context,
                            StringsClass.MessageTagError + response.code(),
                            Toast.LENGTH_LONG).show();
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
                Toast.makeText(
                        context,
                        StringsClass.MessageTagError + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO(Metodo): Realiza la peticion Put para registar datos o actualizar nombre de los toppers.
    public static  void sendNetworkRequestChangeTopperName(final Context context, final int type, final Realm realm, final ToppersDB toppersDB ){
        //inicializa la peticion al metodo correspondiente
        AwsService service = InstanceHttpMethods().create(AwsService.class);

        //Genera el Json que se enviara al WebService
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(StringsClass.idField, toppersDB.getId());
        jsonObject.addProperty(StringsClass.nameField,toppersDB.getName());
        jsonObject.addProperty(StringsClass.userIdField,getUserID(realm));

        //Llama al metodo Call para escuchar la respuesta del WebService
        Call<Void> call = service.changeTopperName(jsonObject);
        call.enqueue(new CustomCallBack<Void>(context){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(
                            context,
                            StringsClass.MessageTagError + response.code(),
                            Toast.LENGTH_LONG).show();
                }
                //Guarda un nuevo registro de un topper en la RealmBD
                if(type == 1){
                    realm.beginTransaction();
                    realm.copyToRealm(toppersDB);
                    realm.commitTransaction();
                }
                //Actualiza un registro de un topper en la RealmBD
                if(type == 2){
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(toppersDB);
                    realm.commitTransaction();
                }

                Intent intent = new Intent(context, Main_Activity.class);
                context.startActivity(intent);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(
                        context,
                        StringsClass.MessageTagError + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}


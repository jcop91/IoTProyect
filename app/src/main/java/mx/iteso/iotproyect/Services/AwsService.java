package mx.iteso.iotproyect.Services;

import com.google.gson.JsonObject;

import mx.iteso.iotproyect.Models.UserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

//TODO(Interfaz): requerida por retrofit para definir los metodos a utilizar,
// Aquí se declara tipo de metodo(Get,Post,Put,Delete) con la parte final de la url,
// Se anexa lo que se envia y lo que recibe en la clase Call.
public interface AwsService {

    @POST("createnewuser")
    Call<UserRequest> createUser(@Body UserRequest user);

    @PUT("changenametopper")
    Call<Void> changeTopperName(@Body JsonObject body);

}

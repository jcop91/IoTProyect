package mx.iteso.iotproyect.Services;

import com.google.gson.JsonObject;

import mx.iteso.iotproyect.Models.UserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AwsService {

    @POST("createnewuser")
    Call<UserRequest> createUser(@Body UserRequest user);

    @PUT("changenametopper")
    Call<Void> changeTopperName(@Body JsonObject body);
}

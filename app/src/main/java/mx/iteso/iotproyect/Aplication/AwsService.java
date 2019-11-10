package mx.iteso.iotproyect.Aplication;

import com.google.gson.JsonObject;

import mx.iteso.iotproyect.Models.UserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AwsService {

    @POST("createnewuser")
    //Call<UserRequest> createUser(@Body JsonObject body);
    Call<UserRequest> createUser(@Body UserRequest user);
    /*@PUT("changenametopper")
    @FormUrlEncoded
    Call<ToppersDB> changeTopperName(@Field("id") String Id, @Field("name") String Name, @Field("userId") String UserId);

    @GET("topperslist")
    Call<ArrayList<ToppersDB>>  getList();*/
}

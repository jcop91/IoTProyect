package mx.iteso.iotproyect.Aplication;

import java.util.ArrayList;

import mx.iteso.iotproyect.Models.Toppers;
import mx.iteso.iotproyect.Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface AwsService {

 /*   @POST("createnewuser")
    Call<User> createUser(@Body User user);

    @PUT("changenametopper")
    @FormUrlEncoded
    Call<Toppers> changeTopperName(@Field("id") String Id, @Field("name") String Name, @Field("userId") String UserId);

    @GET("topperslist")
    Call<ArrayList<Toppers>>  getList();

  */
}

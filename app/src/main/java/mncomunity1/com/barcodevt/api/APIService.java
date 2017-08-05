package mncomunity1.com.barcodevt.api;


import mncomunity1.com.barcodevt.model.Login;
import mncomunity1.com.barcodevt.model.Post;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface APIService {

    @FormUrlEncoded
    @POST("/pmii_mobile_vt/post_machine.php")
    Call<Post> getOrder(@Field("tags") String tags,@Field("loca") String location);


    @FormUrlEncoded
    @POST("/pmii_mobile_vt/login_mobile.php")
    Call<Login> login(@Field("username") String username,@Field("password") String password);





}

package Api;
import java.util.List;

import Model.Futsal1;
import Model.Futsal2;
import Model.LoginResponse;
import Model.RegisterResponse;
import Model.User;
import Model.User2;
import Model.User3;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @POST("api/authuser")
    Call<LoginResponse> getResponse(@Body User2 user2);

    @POST("api/authadmin")
    Call<LoginResponse> getAdminResponse(@Body User2 user2);

    @POST("api/user")
    Call<RegisterResponse> addUsers(@Body User user);

    @POST("api/futsal")
    Call<RegisterResponse> addFutsal(@Body Futsal1 futsal);

    @PUT("api/user")
    Call<RegisterResponse> upuser(@Body User user);

    @PUT("api/futsal")
    Call<RegisterResponse> upFutsal(@Body Futsal1 futsal);

    @GET("api/futsal")
    Call<List<Futsal2>> getFutsal();

    @GET("api/user/{username}")
    Call<List<User3>> getuser(@Path("username") String username);

    @HTTP(method = "DELETE", path = "api/futsal/{name}", hasBody = true)
    Call<Void> deletebook(@Path("name") String name);
}
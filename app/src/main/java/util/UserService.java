package util;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface UserService {
    @GET("bins/w10ke")
    Call<JsonObject> getUsers();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.myjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

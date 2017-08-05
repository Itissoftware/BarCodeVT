package mncomunity1.com.barcodevt.service;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    Context context = new Application().getApplicationContext();
    SharedPreferences settings = context.getSharedPreferences("ip_address", 0);
    String url = settings.getString("ip", "");

    public static final String BASE_URL = "";
    private static Retrofit retrofit = null;


    public static Retrofit getClient(String url) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }
}
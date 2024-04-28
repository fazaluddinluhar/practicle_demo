package com.example.practicaltestfazal.api;

import com.example.practicaltestfazal.utils.Urls;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    public static OkHttpClient okHttpClient1 = new OkHttpClient().newBuilder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build();

    public static Retrofit getClient() {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient1)
                .build();
        return retrofit;
    }
}

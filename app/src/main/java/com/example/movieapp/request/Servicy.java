package com.example.movieapp.request;


import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicy {
    private static Servicy mInstance;
    private Retrofit retrofit;

    private Servicy() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static Servicy getInstance() {
        if (mInstance == null) {
            mInstance = new Servicy();
        }
        return mInstance;
    }

    public MovieApi getJSONApi() {
        return retrofit.create(MovieApi.class);
    }
}

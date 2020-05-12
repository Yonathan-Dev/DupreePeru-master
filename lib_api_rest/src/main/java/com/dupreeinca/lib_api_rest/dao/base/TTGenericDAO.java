package com.dupreeinca.lib_api_rest.dao.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dupreeinca.lib_api_rest.interceptors.AddCookiesInterceptor;
import com.dupreeinca.lib_api_rest.interceptors.ConnectivityInterceptor;
import com.dupreeinca.lib_api_rest.interceptors.ReceivedCookiesInterceptor;
import com.dupreeinca.lib_api_rest.model.base.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TTGenericDAO {
    private String TAG = TTGenericDAO.class.getName();

    private Context context;

    private Retrofit retrofit;

    //eg5st4r67d8is60tjesuru3bi1
    public TTGenericDAO(Context context) {
        this.context = context;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new AddCookiesInterceptor(context));
        client.addInterceptor(new ReceivedCookiesInterceptor(context));
        client.addInterceptor(new ConnectivityInterceptor(context));
        client.addInterceptor(logging);
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

    }

    protected Map<String, String> getParameters(){
        return new HashMap<>();
    }

    protected Map<String, String> getHeaders(){
        return new HashMap<>();
    }

    @NonNull
    protected String getBaseURL() {
        return Api.API_URL;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Context getContext() {
        return context;
    }

}

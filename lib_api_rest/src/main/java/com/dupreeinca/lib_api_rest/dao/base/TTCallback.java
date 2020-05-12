package com.dupreeinca.lib_api_rest.dao.base;

import android.support.annotation.NonNull;

import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

public class TTCallback<T> implements retrofit2.Callback<T> {
//    private Context ctx;
    private TTResultListener<T> listener;

    private Retrofit retrofit;

    public TTCallback(TTResultListener<T> listener, Retrofit retrofit) {
        this.retrofit = retrofit;
        this.listener = listener;
    }

    @Override
    public void onResponse(@NonNull retrofit2.Call<T> call, @NonNull Response<T> response) {
        int code=response.code();
        TTError ttError;
        String jsonInString = "";
        if(code==200 || code==400 || code==401 || code==404 || code==501) {
            if (!response.isSuccessful()) {


                try {
                    jsonInString = response.errorBody().string();
                    ttError = new Gson().fromJson(jsonInString, TTError.class);
                    if (ttError != null && ttError.getRaise() != null) {
                        ttError.setMessage(ttError.getRaise().get(0).getField().concat(". ").concat(ttError.getRaise().get(0).getError()));
                        ttError.setErrorBody(jsonInString);
                        ttError.setStatusCode(response.code());
                        listener.error(ttError);
                        return;
                    }
                } catch (IOException | JsonSyntaxException ignored) {
                }

                ttError = TTError.errorFromMessage("Por favor intente nuevamente");
                ttError.setErrorBody(jsonInString);
                ttError.setStatusCode(response.code());
                listener.error(ttError);
                return;
            }
            listener.success(response.body());
            return;
        }

        ttError = TTError.errorFromMessage("Por favor intente nuevamente");
        ttError.setErrorBody(jsonInString);
        ttError.setStatusCode(response.code());
        listener.error(ttError);
    }

    @Override
    public void onFailure(retrofit2.Call<T> call, Throwable t) {
        listener.error(TTError.errorFromMessage("Por favor intente nuevamente"));
    }

//    private void gotoMain(){
//        mPreferences.cerrarSesion(ctx);
//        ctx.startActivity(new Intent(ctx, MainActivity.class));
//        if(((MenuActivity)ctx)!=null){
//            ((MenuActivity)ctx).finish();
//        }
//    }
}
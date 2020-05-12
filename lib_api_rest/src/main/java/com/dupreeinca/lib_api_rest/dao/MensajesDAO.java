package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.IdMessages;
import com.dupreeinca.lib_api_rest.model.dto.response.BandejaDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class MensajesDAO extends TTGenericDAO {

    public MensajesDAO(Context context) {
        super(context);
    }

    public void getMessages(final TTResultListener<BandejaDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<BandejaDTO> call = rest.getMessages();
        call.enqueue(new TTCallback<BandejaDTO>(new TTResultListener<BandejaDTO>() {
            @Override
            public void success(BandejaDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void readMessages(IdMessages data, final TTResultListener<GenericDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<GenericDTO> call = rest.readMessages(new Gson().toJson(data));
        call.enqueue(new TTCallback<GenericDTO>(new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void deleteMessages(IdMessages data, final TTResultListener<GenericDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<GenericDTO> call = rest.deleteMessages(new Gson().toJson(data));
        call.enqueue(new TTCallback<GenericDTO>(new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }






    public interface iRest {
        //@FormUrlEncoded
        @POST("mensajes/consulta")
        Call<BandejaDTO> getMessages();

        @FormUrlEncoded
        @POST("mensajes/actualiza")
        Call<GenericDTO> readMessages(@Field("Params") String jsonUsuario);

        @FormUrlEncoded
        @POST("mensajes/borrar")
        Call<GenericDTO> deleteMessages(@Field("Params") String jsonUsuario);
    }
}

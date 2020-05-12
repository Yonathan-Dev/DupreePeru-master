package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.UserDTO;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.response.DataAuth;
import com.dupreeinca.lib_api_rest.model.dto.response.DataUser;
import com.dupreeinca.lib_api_rest.model.dto.response.PerfilDTO;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class UserDAO extends TTGenericDAO {

    public UserDAO(Context context) {
        super(context);
    }

    public void retrieveUser(final TTResultListener<UserDTO> listener){
        UserREST userREST = getRetrofit().create(UserREST.class);
        Call<UserDTO> call = userREST.retrieveUser();
        call.enqueue(new TTCallback<UserDTO>(new TTResultListener<UserDTO>() {
            @Override
            public void success(UserDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getPerfilUser(Identy data, final TTResultListener<PerfilDTO> listener){
        UserREST userREST = getRetrofit().create(UserREST.class);
        Call<PerfilDTO> call = userREST.getPerfilUser(new Gson().toJson(data));
        call.enqueue(new TTCallback<PerfilDTO>(new TTResultListener<PerfilDTO>() {
            @Override
            public void success(PerfilDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void putPerfil(DataUser data, final TTResultListener<DataAuth> listener){
        UserREST userREST = getRetrofit().create(UserREST.class);
        Call<DataAuth> call = userREST.putPerfil(new Gson().toJson(data));
        call.enqueue(new TTCallback<DataAuth>(new TTResultListener<DataAuth>() {
            @Override
            public void success(DataAuth result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    private interface UserREST {

        @GET("users/2")
        Call<UserDTO> retrieveUser();

        @FormUrlEncoded
        @POST("reportes/datos_perfil")
        Call<PerfilDTO> getPerfilUser(@Field("Params") String jsonCedula);//el parametro filter no va, solo que
        //la libreria no permite parametros vacios en POST

        @FormUrlEncoded
        @POST("panel/modifica_perfil")
        Call<DataAuth> putPerfil(@Field("Params") String jsonPerfil);

    }
}

package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.ApprovePreIns;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.request.IdentyName;
import com.dupreeinca.lib_api_rest.model.dto.request.InscriptionDTO;
import com.dupreeinca.lib_api_rest.model.dto.request.ListPre;
import com.dupreeinca.lib_api_rest.model.dto.request.Register;
import com.dupreeinca.lib_api_rest.model.dto.request.ValidateRef;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListBarrioDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPreinscripcionDTO;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 2/21/19.
 */

public class InscripcionDAO extends TTGenericDAO {
    public InscripcionDAO(Context context) {
        super(context);
    }

    public void getInscripcion(Identy data, final TTResultListener<InscriptionDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<InscriptionDTO> call = rest.getInscripcion(new Gson().toJson(data));
        call.enqueue(new TTCallback<InscriptionDTO>(new TTResultListener<InscriptionDTO>() {
            @Override
            public void success(InscriptionDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void validateReferido(ValidateRef data, final TTResultListener<GenericDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<GenericDTO> call = rest.validateReferido(new Gson().toJson(data));
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

    public void getBarrios(String id_ciudad, final TTResultListener<ListBarrioDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<ListBarrioDTO> call = rest.getBarrios(id_ciudad);
        call.enqueue(new TTCallback<ListBarrioDTO>(new TTResultListener<ListBarrioDTO>() {
            @Override
            public void success(ListBarrioDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void postIncripcion(InscriptionDTO data, final TTResultListener<GenericDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<GenericDTO> call = rest.postIncripcion(new Gson().toJson(data));
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

    public void getListaRegistradas(IdentyName data, final TTResultListener<ListPreinscripcionDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<ListPreinscripcionDTO> call = rest.getListaRegistradas(new Gson().toJson(data));
        call.enqueue(new TTCallback<ListPreinscripcionDTO>(new TTResultListener<ListPreinscripcionDTO>() {
            @Override
            public void success(ListPreinscripcionDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getListPre(ListPre data, final TTResultListener<ListPreinscripcionDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<ListPreinscripcionDTO> call = rest.getListPre(data.getPerfil(), data.getIndex_pages(), data.getValor(), data.getToken());
        call.enqueue(new TTCallback<ListPreinscripcionDTO>(new TTResultListener<ListPreinscripcionDTO>() {
            @Override
            public void success(ListPreinscripcionDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void aprobarPreinscripcion(ApprovePreIns data, final TTResultListener<GenericDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<GenericDTO> call = rest.aprobarPreinscripcion(new Gson().toJson(data));
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

    public void validateCentralRiesgo(Identy data, final TTResultListener<GenericDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<GenericDTO> call = rest.validateCentralRiesgo(new Gson().toJson(data));
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

    public void postPreinscripcion(Register data, final TTResultListener<GenericDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<GenericDTO> call = rest.postPreinscripcion(new Gson().toJson(data));
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
        @FormUrlEncoded
        @POST("panel/actualiza_pre")
        Call<InscriptionDTO> getInscripcion(@Field("Params") String jsonCedula);

        @FormUrlEncoded
        @POST("reportes/nombre")
        Call<GenericDTO> validateReferido(@Field("Params") String jsonValidaRef);

        @GET("panel/barrios/")
        Call<ListBarrioDTO> getBarrios(@Query("id_ciudad") String id_ciudad);

        @FormUrlEncoded
        @POST("panel/inscripcion")
        Call<GenericDTO> postIncripcion(@Field("Params") String jsonInscription);

        @GET("panel/lista_preinscripcion")
        Call<ListPreinscripcionDTO> getListPre(
                @Query("perfil") String perfil,
                @Query("index_pages") int index_pages,
                @Query("valor") String valor,
                @Query("token") String token
        );

        @FormUrlEncoded
        @POST("panel/consulta_preinscripcion")
        Call<ListPreinscripcionDTO> getListaRegistradas(
                @Field("Params") String json
        );

        @FormUrlEncoded
        @POST("panel/estado_prelider")
        Call<GenericDTO> aprobarPreinscripcion(@Field("Params") String json);

        @FormUrlEncoded
        @POST("reportes/cifin")
        Call<GenericDTO> validateCentralRiesgo(@Field("Params") String json);

        @FormUrlEncoded
        @POST("panel/pre_inscripcion")
        Call<GenericDTO> postPreinscripcion(@Field("Params") String json);
    }
}

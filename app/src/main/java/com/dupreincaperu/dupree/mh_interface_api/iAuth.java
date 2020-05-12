package com.dupreincaperu.dupree.mh_interface_api;

import com.dupreeinca.lib_api_rest.model.dto.response.DataAuth;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by cloudemotion on 27/8/17.
 */

public interface iAuth {
    //login/authenticate/
    @FormUrlEncoded
    @POST("login/authenticate")
    Call<DataAuth> auth(@Field("Params") String dataAuth);

    @FormUrlEncoded
    @POST("panel/olvido_contrasena")
    Call<GenericDTO> notifyForgot(@Field("Params") String identy);

    @FormUrlEncoded
    @POST("panel/valida_codigo")
    Call<GenericDTO> validateCode(@Field("Params") String codigo);

    @FormUrlEncoded
    @POST("panel/valida_contrasena")
    Call<GenericDTO> validatePassword(@Field("Params") String pwd);

    @FormUrlEncoded
    @POST("panel/pre_inscripcion")
    Call<GenericDTO> preinscripcion(@Field("Params") String preinscripcion);

    @FormUrlEncoded
    @POST("panel/vuelvete_asesora")
    Call<GenericDTO> vuelveteAsesora(@Field("Params") String preinscripcion);


    @FormUrlEncoded
    @POST("panel/terminos")
    Call<GenericDTO> termins(@Field("Params") String termins);

    @FormUrlEncoded
    @POST("panel/terminos_gerente")
    Call<GenericDTO> terminsGerente(@Field("Params") String termins);

    @FormUrlEncoded
    @POST("panel/firebase")
    Call<GenericDTO> refreshToken(@Field("Params") String firebase);
}

package com.dupreincaperu.dupree.mh_interface_api;

import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by cloudemotion on 30/8/17.
 */

public interface iFile {

    @Multipart
    @POST("panel/fotos")
    Call<GenericDTO> uploadImage(@Part MultipartBody.Part imagen/*, @Part("filename") RequestBody filename*/);

    @Multipart
    @POST("panel/imagen_perfil")
    Call<GenericDTO> uploadImageProfile(@Part MultipartBody.Part imagen/*, @Part("filename") RequestBody filename*/);


    //ESTE METODO SE USA CUANDO EL ARCHIVO ES ESTATICO (base mas esto)
    @GET("/resource/example.zip")
    Call<ResponseBody> downloadFileWithFixedUrl();

    //ESTE SE USA CUANDO LA URL DEL ARCHIVO ES DINAMICA
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);



    @GET
    @Streaming
    Call<ResponseBody> getFile(@Url String url);

}

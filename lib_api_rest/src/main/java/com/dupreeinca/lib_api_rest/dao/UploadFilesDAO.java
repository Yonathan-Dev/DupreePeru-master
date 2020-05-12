package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
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

public class UploadFilesDAO extends TTGenericDAO {

    public UploadFilesDAO(Context context) {
        super(context);
    }

    public void uploadImage(@Part MultipartBody.Part partFile, final TTResultListener<GenericDTO> listener){
        UserREST rest = getRetrofit().create(UserREST.class);
        Call<GenericDTO> call = rest.uploadImage(partFile);
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

    public void getFile(String url, final TTResultListener<ResponseBody> listener){
        UserREST rest = getRetrofit().create(UserREST.class);
        Call<ResponseBody> call = rest.getFile(url);
        call.enqueue(new TTCallback<ResponseBody>(new TTResultListener<ResponseBody>() {
            @Override
            public void success(ResponseBody result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    private interface UserREST {

        @Multipart
        @POST("panel/fotos")
        Call<GenericDTO> uploadImage(@Part MultipartBody.Part partFile/*, @Part("filename") RequestBody filename*/);

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
}

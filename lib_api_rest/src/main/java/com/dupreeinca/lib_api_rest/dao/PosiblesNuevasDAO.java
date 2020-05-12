package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.PosiblesNuevasSend;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by marwuinh@gmail.com on 2/21/19.
 */

public class PosiblesNuevasDAO extends TTGenericDAO {
    public PosiblesNuevasDAO(Context context) {
        super(context);
    }

    public void postEnviar(PosiblesNuevasSend data, final TTResultListener<GenericDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<GenericDTO> call = rest.postEnviar(new Gson().toJson(data));
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
        @POST("panel/posingreso")
        Call<GenericDTO> postEnviar(@Field("Params") String json);
    }
}

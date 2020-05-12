package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCampana;

import retrofit2.Call;
import retrofit2.http.GET;

public class CampanasDAO extends TTGenericDAO {

    public CampanasDAO(Context context) {
        super(context);
    }

    public void getCampanas(final TTResultListener<ListCampana> listener){
        UserREST rest = getRetrofit().create(UserREST.class);
        Call<ListCampana> call = rest.getCampanas();
        call.enqueue(new TTCallback<ListCampana>(new TTResultListener<ListCampana>() {
            @Override
            public void success(ListCampana result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }


    private interface UserREST {
        @GET("reportes/campanas")
        Call<ListCampana> getCampanas();
    }
}

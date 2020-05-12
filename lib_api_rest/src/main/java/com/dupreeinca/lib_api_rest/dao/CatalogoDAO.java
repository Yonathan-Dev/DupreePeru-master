package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.CatalogoPremiosList;

import retrofit2.Call;
import retrofit2.http.GET;

public class CatalogoDAO extends TTGenericDAO {

    public CatalogoDAO(Context context) {
        super(context);
    }

    public void getCatalogoPremios(final TTResultListener<CatalogoPremiosList> listener){
        Rest rest = getRetrofit().create(Rest.class);
        Call<CatalogoPremiosList> call = rest.getCatalogoPremios();
        call.enqueue(new TTCallback<CatalogoPremiosList>(new TTResultListener<CatalogoPremiosList>() {
            @Override
            public void success(CatalogoPremiosList result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    private interface Rest {

        @GET("panel/folletos")
        Call<CatalogoPremiosList> getCatalogoPremios();

    }
}

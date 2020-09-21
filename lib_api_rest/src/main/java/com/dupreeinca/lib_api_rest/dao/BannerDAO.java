package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.BannerDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ProductCatalogoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.UrlsCatalogosDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.VersionDTO;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class BannerDAO extends TTGenericDAO {

    public BannerDAO(Context context) {
        super(context);
    }

    public void getBanner(final TTResultListener<BannerDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<BannerDTO> call = rest.getBanner();
        call.enqueue(new TTCallback<BannerDTO>(new TTResultListener<BannerDTO>() {
            @Override
            public void success(BannerDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getUrlCatalogos(final TTResultListener<UrlsCatalogosDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<UrlsCatalogosDTO> call = rest.getUrlCatalogos();
        call.enqueue(new TTCallback<UrlsCatalogosDTO>(new TTResultListener<UrlsCatalogosDTO>() {
            @Override
            public void success(UrlsCatalogosDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void obtainVersion(final TTResultListener<VersionDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<VersionDTO> call = rest.obtainVersion();
        call.enqueue(new TTCallback<VersionDTO>(new TTResultListener<VersionDTO>() {
            @Override
            public void success(VersionDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getProductos(final String campanaActual, final String cedula, final TTResultListener<ProductCatalogoDTO> listener){
        iRest rest = getRetrofit().create(iRest.class);
        Call<ProductCatalogoDTO> call = rest.getProductos(new Gson().toJson(campanaActual), cedula);
        call.enqueue(new TTCallback<ProductCatalogoDTO>(new TTResultListener<ProductCatalogoDTO>() {
            @Override
            public void success(ProductCatalogoDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }



    public interface iRest {
        @GET("panel/banner/")
        Call<BannerDTO> getBanner();

        @GET("panel/catalogos")
        Call<UrlsCatalogosDTO> getUrlCatalogos();

        @GET("panel/version/")
        Call<VersionDTO> obtainVersion();

        @GET("pedidos/productos")
        Call<ProductCatalogoDTO> getProductos(@Query("filters") String campana, @Query("cedula") String cedula);

    }
}

package com.dupreeinca.lib_api_rest.controller;

import android.content.Context;
import android.util.Log;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.base.TTGenericController;
import com.dupreeinca.lib_api_rest.dao.BannerDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.BannerDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ProductCatalogoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.UrlsCatalogosDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.VersionDTO;


public class BannerController extends TTGenericController {
    private String TAG = BannerController.class.getName();

    public BannerController(Context context) {
        super(context);
    }


    public void getBanner(final TTResultListener<BannerDTO> listener){
        Log.e(TAG, "obtainBanner()");
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        BannerDAO dao = new BannerDAO(getContext());
        dao.getBanner(new TTResultListener<BannerDTO>() {
            @Override
            public void success(BannerDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void getUrlCatalogos(final TTResultListener<UrlsCatalogosDTO> listener){
        Log.e(TAG, "obtainVersion()");
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        BannerDAO dao = new BannerDAO(getContext());
        dao.getUrlCatalogos(new TTResultListener<UrlsCatalogosDTO>() {
            @Override
            public void success(UrlsCatalogosDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }


    public void obtainVersion(final TTResultListener<VersionDTO> listener){
        Log.e(TAG, "obtainVersion()");
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        BannerDAO dao = new BannerDAO(getContext());
        dao.obtainVersion(new TTResultListener<VersionDTO>() {
            @Override
            public void success(VersionDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void getProductos(final String campana, final String cedula, final TTResultListener<ProductCatalogoDTO> listener){

        Log.e(TAG, "obtainVersion()");
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        BannerDAO dao = new BannerDAO(getContext());
        dao.getProductos(campana, cedula, new TTResultListener<ProductCatalogoDTO>() {
            @Override
            public void success(ProductCatalogoDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
//                try {
//                    String jsonInString = response.errorBody().string();
//                    //Log.e(TAG, "Retrofit Response : " + jsonInString);
//                    ProductCatalogoDTO resp = new Gson().fromJson(jsonInString, ProductCatalogoDTO.class);
//
//                    if(code==404){
//                        ((FullscreenActivity) myContext).responseCatalogo(resp.getResult());
//                    }else {
//                        msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                    }
//
//                } catch (IOException | JsonSyntaxException e) {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }

                listener.error(error);
            }
        });
    }
}

package com.dupreeinca.lib_api_rest.controller;

import android.content.Context;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.base.TTGenericController;
import com.dupreeinca.lib_api_rest.dao.CatalogoDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.CatalogoPremiosList;


public class CatalogosController extends TTGenericController {

    public CatalogosController(Context context) {
        super(context);
    }

    public void getCatalogoPremios(final TTResultListener<CatalogoPremiosList> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        CatalogoDAO dao = new CatalogoDAO(getContext());
        dao.getCatalogoPremios(new TTResultListener<CatalogoPremiosList>() {
            @Override
            public void success(CatalogoPremiosList result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }
}

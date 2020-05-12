package com.dupreeinca.lib_api_rest.controller;

import android.content.Context;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.base.TTGenericController;
import com.dupreeinca.lib_api_rest.dao.CampanasDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCampana;


public class CampanaController extends TTGenericController {

    public CampanaController(Context context) {
        super(context);
    }

    public void getCampanas(final TTResultListener<ListCampana> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        CampanasDAO dao = new CampanasDAO(getContext());
        dao.getCampanas(new TTResultListener<ListCampana>() {
            @Override
            public void success(ListCampana result) {
                //Errror de Backend
                if(result.getCode() == 404 || result.getCode() == 501){
                    listener.error(TTError.errorFromMessage(result.getRaise().get(0).getField().concat(". ").concat(result.getRaise().get(0).getError())));
                } else {
                    listener.success(result);
                }
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }
}

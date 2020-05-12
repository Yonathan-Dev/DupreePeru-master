package com.dupreeinca.lib_api_rest.controller;

import android.content.Context;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.base.TTGenericController;
import com.dupreeinca.lib_api_rest.dao.MensajesDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.IdMessages;
import com.dupreeinca.lib_api_rest.model.dto.response.BandejaDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;


public class MensajesController extends TTGenericController {

    public MensajesController(Context context) {
        super(context);
    }

    public void getMessages(final TTResultListener<BandejaDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        MensajesDAO dao = new MensajesDAO(getContext());
        dao.getMessages(new TTResultListener<BandejaDTO>() {
            @Override
            public void success(BandejaDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void readMessages(IdMessages data, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        MensajesDAO dao = new MensajesDAO(getContext());
        dao.readMessages(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void deleteMessages(IdMessages data, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        MensajesDAO dao = new MensajesDAO(getContext());
        dao.deleteMessages(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }
}

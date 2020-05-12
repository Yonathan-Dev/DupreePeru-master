package com.dupreeinca.lib_api_rest.controller;

import android.content.Context;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.base.TTGenericController;
import com.dupreeinca.lib_api_rest.dao.UserDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.UserDTO;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.response.DataAuth;
import com.dupreeinca.lib_api_rest.model.dto.response.DataUser;
import com.dupreeinca.lib_api_rest.model.dto.response.PerfilDTO;


public class UserController extends TTGenericController {

    public UserController(Context context) {
        super(context);
    }

    public void retrieveUser(final TTResultListener<UserDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        UserDAO userDAO = new UserDAO(getContext());
        userDAO.retrieveUser(new TTResultListener<UserDTO>() {
            @Override
            public void success(UserDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void getPerfilUser(Identy data, final TTResultListener<PerfilDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        UserDAO dao = new UserDAO(getContext());
        dao.getPerfilUser(data,new TTResultListener<PerfilDTO>() {
            @Override
            public void success(PerfilDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void putPerfil(DataUser data, final TTResultListener<DataAuth> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        UserDAO dao = new UserDAO(getContext());
        dao.putPerfil(data, new TTResultListener<DataAuth>() {
            @Override
            public void success(DataAuth result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }
}

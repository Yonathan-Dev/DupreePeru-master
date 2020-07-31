package com.dupreeinca.lib_api_rest.controller;

import android.content.Context;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.base.TTGenericController;
import com.dupreeinca.lib_api_rest.dao.InscripcionDAO;
import com.dupreeinca.lib_api_rest.dao.PosiblesNuevasDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.ApprovePreIns;
import com.dupreeinca.lib_api_rest.model.dto.request.ElimPreIns;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.request.IdentyName;
import com.dupreeinca.lib_api_rest.model.dto.request.InscriptionDTO;
import com.dupreeinca.lib_api_rest.model.dto.request.ListPre;
import com.dupreeinca.lib_api_rest.model.dto.request.PosiblesNuevasSend;
import com.dupreeinca.lib_api_rest.model.dto.request.Register;
import com.dupreeinca.lib_api_rest.model.dto.request.ValidateRef;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListBarrioDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPreinscripcionDTO;


public class InscripcionController extends TTGenericController {

    public InscripcionController(Context context) {
        super(context);
    }

    public void getInscripcion(Identy data, final TTResultListener<InscriptionDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.getInscripcion(data, new TTResultListener<InscriptionDTO>() {
            @Override
            public void success(InscriptionDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void validateReferido(ValidateRef data, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.validateReferido(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                //Errror de Backend
                if(result.getCode() == 404){
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

    public void getBarrios(String id_ciudad, final TTResultListener<ListBarrioDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.getBarrios(id_ciudad, new TTResultListener<ListBarrioDTO>() {
            @Override
            public void success(ListBarrioDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void postIncripcion(InscriptionDTO data,final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.postIncripcion(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                //Errror de Backend
                if(result.getCode() == 404){
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

    public void getListPre(ListPre data, final TTResultListener<ListPreinscripcionDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.getListPre(data, new TTResultListener<ListPreinscripcionDTO>() {
            @Override
            public void success(ListPreinscripcionDTO result) {
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

    public void getListaRegistradas(IdentyName data, final TTResultListener<ListPreinscripcionDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.getListaRegistradas(data, new TTResultListener<ListPreinscripcionDTO>() {
            @Override
            public void success(ListPreinscripcionDTO result) {
                //Errror de Backend
                if(result.getCode() == 404){
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

    public void aprobarPreinscripcion(ApprovePreIns data, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.aprobarPreinscripcion(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                //Errror de Backend
                if(result.getCode() == 404){
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

    public void eliminarPreinscripcion(ElimPreIns data, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.eliminarPreinscripcion(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                //Errror de Backend
                if(result.getCode() == 404){
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

    public void validateCentralRiesgo(Identy data, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.validateCentralRiesgo(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                //Errror de Backend
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void postPreinscripcion(Register data, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        InscripcionDAO dao = new InscripcionDAO(getContext());
        dao.postPreinscripcion(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
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

    public void postPosiblesNuevas(PosiblesNuevasSend data, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        PosiblesNuevasDAO dao = new PosiblesNuevasDAO(getContext());
        dao.postEnviar(data, new TTResultListener<GenericDTO>() {
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

package com.dupreeinca.lib_api_rest.controller;

import android.content.Context;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.base.TTGenericController;
import com.dupreeinca.lib_api_rest.dao.ReportesDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.HistorialDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCDR;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCupoSaldoConf;
import com.dupreeinca.lib_api_rest.model.dto.response.ListFactura;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPQR;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPagosRealizados;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPanelGte;
import com.dupreeinca.lib_api_rest.model.dto.response.PanelAsesoraDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.PuntosAsesoraDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ReferidosDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.RequeridUbicacion;
import com.dupreeinca.lib_api_rest.model.dto.response.RetenidosDTO;


public class ReportesController extends TTGenericController {

    public ReportesController(Context context) {
        super(context);
    }

    public void getCDR(Identy data, final TTResultListener<ListCDR> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getCDR(data, new TTResultListener<ListCDR>() {
            @Override
            public void success(ListCDR result) {
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

    public void getPQR(Identy data, final TTResultListener<ListPQR> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getPQR(data, new TTResultListener<ListPQR>() {
            @Override
            public void success(ListPQR result) {
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

    public void getFacturasPDF(Identy data, final TTResultListener<ListFactura> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getFacturasPDF(data, new TTResultListener<ListFactura>() {
            @Override
            public void success(ListFactura result) {
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

    public void getPagos(Identy data, final TTResultListener<ListPagosRealizados> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getPagos(data, new TTResultListener<ListPagosRealizados>() {
            @Override
            public void success(ListPagosRealizados result) {
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

    public void getCupoSaldoConf(Identy data, final TTResultListener<ListCupoSaldoConf> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getCupoSaldoConf(data, new TTResultListener<ListCupoSaldoConf>() {
            @Override
            public void success(ListCupoSaldoConf result) {
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

    public void getPanelGrte(final String token, final TTResultListener<ListPanelGte> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getPanelGrte(token, new TTResultListener<ListPanelGte>() {
            @Override
            public void success(ListPanelGte result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void getHistorialPedido(Identy data, final TTResultListener<HistorialDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getHistorialPedido(data, new TTResultListener<HistorialDTO>() {
            @Override
            public void success(HistorialDTO result) {
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

    public void getPanelAsesora(final TTResultListener<PanelAsesoraDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getPanelAsesora(new TTResultListener<PanelAsesoraDTO>() {
            @Override
            public void success(PanelAsesoraDTO result) {
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

    public void getPuntosAsesora(Identy data, final TTResultListener<PuntosAsesoraDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getPuntosAsesora(data, new TTResultListener<PuntosAsesoraDTO>() {
            @Override
            public void success(PuntosAsesoraDTO result) {
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

    public void getIncentivosReferido(Identy data, final TTResultListener<ReferidosDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getIncentivosReferido(data, new TTResultListener<ReferidosDTO>() {
            @Override
            public void success(ReferidosDTO result) {
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

    public void getPedRetenidos(Identy data, final TTResultListener<RetenidosDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.getPedRetenidos(data, new TTResultListener<RetenidosDTO>() {
            @Override
            public void success(RetenidosDTO result) {
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

    public void reporteUbicacionCliente(RequeridUbicacion data, final TTResultListener<GenericDTO> listener) {
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        ReportesDAO dao = new ReportesDAO(getContext());
        dao.reporteUbicacionCliente(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                //Errror de Backend
                if(result.getCode() == 404 || result.getCode()==501){
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

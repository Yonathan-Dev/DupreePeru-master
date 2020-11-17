package com.dupreeinca.lib_api_rest.controller;

import android.content.Context;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.base.TTGenericController;
import com.dupreeinca.lib_api_rest.dao.PedidosDAO;
import com.dupreeinca.lib_api_rest.enums.EnumLiquidar;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.request.LiquidarMensajeSend;
import com.dupreeinca.lib_api_rest.model.dto.request.LiquidarSend;
import com.dupreeinca.lib_api_rest.model.dto.request.PrePedidoSend;
import com.dupreeinca.lib_api_rest.model.dto.request.RedimirPremios;
import com.dupreeinca.lib_api_rest.model.dto.response.EstadoPedidoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.EstadoPrePedidoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.FaltantesDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.LiquidarDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.RedimirDTO;

public class PedidosController extends TTGenericController {

    public PedidosController(Context context) {
        super(context);
    }

    public void liquidarPedido(LiquidarSend data, final TTResultListener<LiquidarDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        PedidosDAO dao = new PedidosDAO(getContext());
        dao.liquidarPedido(data, new TTResultListener<LiquidarDTO>() {
            @Override
            public void success(LiquidarDTO result) {
                //Errror de Backend
                if(result.getCode() == 404 || result.getCode()==501){
                    listener.error(TTError.errorFromMessage(result.getRaise().get(0).getField().concat(". ").concat(result.getRaise().get(0).getError())));
                } else {
                    listener.success(result);
                }
            }

            @Override
            public void error(TTError error) {
                if(error.getStatusCode() == 404 && error.getCodigo().equals(EnumLiquidar.DEBAJO_MONTO.getKey())){
                    //Rechazado porque no cumple con monto minimo
                    listener.success(new LiquidarDTO(error.getMessage(), error.getTotal_pedido(), error.getCodigo()));
                } else {
                    listener.error(error);
                }
            }
        });
    }


    public void liquidarPedidoMensaje(LiquidarMensajeSend data, final TTResultListener<LiquidarDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        PedidosDAO dao = new PedidosDAO(getContext());
        dao.liquidarPedidoMensaje(data, new TTResultListener<LiquidarDTO>() {
            @Override
            public void success(LiquidarDTO result) {
                //Errror de Backend
                if(result.getCode() == 404 || result.getCode()==501){
                    listener.error(TTError.errorFromMessage(result.getRaise().get(0).getField().concat(". ").concat(result.getRaise().get(0).getError())));
                } else {
                    listener.success(result);
                }
            }

            @Override
            public void error(TTError error) {
                if(error.getStatusCode() == 404 && error.getCodigo().equals(EnumLiquidar.DEBAJO_MONTO.getKey())){
                    //Rechazado porque no cumple con monto minimo
                    listener.success(new LiquidarDTO(error.getMessage(), error.getTotal_pedido(), error.getCodigo()));
                } else {
                    listener.error(error);
                }
            }
        });
    }


    //Controller PrePedidos
    public void controllerPrePedido(PrePedidoSend data, final TTResultListener<LiquidarDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        PedidosDAO dao = new PedidosDAO(getContext());

        dao.prePedidoDAO(data, new TTResultListener<LiquidarDTO>() {
            @Override
            public void success(LiquidarDTO result) {
                //Errror de Backend
                if(result.getCode() == 404){
                    listener.error(TTError.errorFromMessage(result.getRaise().get(0).getField().concat(". ").concat(result.getRaise().get(0).getError())));
                } else {
                    listener.success(result);
                }
            }

            @Override
            public void error(TTError error) {
                if(error.getStatusCode() == 404 && error.getCodigo().equals(EnumLiquidar.DEBAJO_MONTO.getKey())){
                    //Rechazado porque no cumple con monto minimo
                    listener.success(new LiquidarDTO(error.getMessage(), error.getTotal_pedido(), error.getCodigo()));
                } else {
                    listener.error(error);
                }
            }
        });
    }




    public void getEstadoPedido(Identy data, final TTResultListener<EstadoPedidoDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        PedidosDAO dao = new PedidosDAO(getContext());
        dao.getEstadoPedido(data, new TTResultListener<EstadoPedidoDTO>() {
            @Override
            public void success(EstadoPedidoDTO result) {
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

    public void getEstadoPrePedido(Identy data, final TTResultListener<EstadoPrePedidoDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        PedidosDAO dao = new PedidosDAO(getContext());
        dao.getEstadoPrePedido(data, new TTResultListener<EstadoPrePedidoDTO>() {
            @Override
            public void success(EstadoPrePedidoDTO result) {
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

    public void getFaltantes(final TTResultListener<FaltantesDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        PedidosDAO dao = new PedidosDAO(getContext());
        dao.getFaltantes(new TTResultListener<FaltantesDTO>() {
            @Override
            public void success(FaltantesDTO result) {
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

    public void getRedimirIncentivos(final TTResultListener<RedimirDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        PedidosDAO dao = new PedidosDAO(getContext());
        dao.getRedimirIncentivos(new TTResultListener<RedimirDTO>() {
            @Override
            public void success(RedimirDTO result) {
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

    public void redimirPremios(RedimirPremios data, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        PedidosDAO dao = new PedidosDAO(getContext());
        dao.redimirPremios(data, new TTResultListener<GenericDTO>() {
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

}

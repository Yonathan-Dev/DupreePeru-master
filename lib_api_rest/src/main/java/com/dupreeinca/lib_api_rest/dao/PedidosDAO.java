package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.request.Index;
import com.dupreeinca.lib_api_rest.model.dto.request.LiquidarSend;
import com.dupreeinca.lib_api_rest.model.dto.request.PrePedidoSend;
import com.dupreeinca.lib_api_rest.model.dto.request.RedimirPremios;
import com.dupreeinca.lib_api_rest.model.dto.response.EstadoPedidoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.EstadoPrePedidoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.FaltantesDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.LiquidarDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.RedimirDTO;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class PedidosDAO extends TTGenericDAO {

    public PedidosDAO(Context context) {
        super(context);
    }

    public void liquidarPedido(LiquidarSend data, final TTResultListener<LiquidarDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<LiquidarDTO> call = userREST.liquidarPedido(new Gson().toJson(data));
        call.enqueue(new TTCallback<LiquidarDTO>(new TTResultListener<LiquidarDTO>() {
            @Override
            public void success(LiquidarDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void prePedidoDAO(PrePedidoSend data, final TTResultListener<LiquidarDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        //AJUSAR REST
        Call<LiquidarDTO> call = userREST.prePredidosREST(new Gson().toJson(data));
        call.enqueue(new TTCallback<LiquidarDTO>(new TTResultListener<LiquidarDTO>() {
            @Override
            public void success(LiquidarDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }


    public void getEstadoPedido(Identy data, final TTResultListener<EstadoPedidoDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<EstadoPedidoDTO> call = userREST.getEstadoPedido(new Gson().toJson(data));
        call.enqueue(new TTCallback<EstadoPedidoDTO>(new TTResultListener<EstadoPedidoDTO>() {
            @Override
            public void success(EstadoPedidoDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getEstadoPrePedido(Identy data, final TTResultListener<EstadoPrePedidoDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<EstadoPrePedidoDTO> call = userREST.getEstadoPrePedido(new Gson().toJson(data));
        call.enqueue(new TTCallback<EstadoPrePedidoDTO>(new TTResultListener<EstadoPrePedidoDTO>() {
            @Override
            public void success(EstadoPrePedidoDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getFaltantes(final TTResultListener<FaltantesDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<FaltantesDTO> call = userREST.getFaltantes();
        call.enqueue(new TTCallback<FaltantesDTO>(new TTResultListener<FaltantesDTO>() {
            @Override
            public void success(FaltantesDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getRedimirIncentivos(final TTResultListener<RedimirDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<RedimirDTO> call = userREST.getRedimirIncentivos(
                new Gson().toJson(new Identy("")),
                new Gson().toJson(new Index("1")));
        call.enqueue(new TTCallback<RedimirDTO>(new TTResultListener<RedimirDTO>() {
            @Override
            public void success(RedimirDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void redimirPremios(RedimirPremios data, final TTResultListener<GenericDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<GenericDTO> call = userREST.redimirPremios(new Gson().toJson(data));
        call.enqueue(new TTCallback<GenericDTO>(new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }


    private interface Rest {

        @FormUrlEncoded
        @POST("pedidos/liquida")
        Call<LiquidarDTO> liquidarPedido(@Field("Params") String jsonLiquidate);

        @FormUrlEncoded
        @POST("pedidos/gprepedido")
        Call<LiquidarDTO> prePredidosREST(@Field("Params") String jsonLiquidate);

        @GET("pedidos/estado")
        Call<EstadoPedidoDTO> getEstadoPedido(@Query("filters") String jsonCedula);

        @GET("pedidos/estadopre")
        Call<EstadoPrePedidoDTO> getEstadoPrePedido(@Query("filters") String jsonCedula);

        @GET("pedidos/faltante")
        Call<FaltantesDTO> getFaltantes();

        @GET("pedidos/premios")
        Call<RedimirDTO> getRedimirIncentivos(
                @Query("filters") String jsonCedula, @Query("paginator") String pag
        );

        @FormUrlEncoded
        @POST("pedidos/redime_premios")
        Call<GenericDTO> redimirPremios(@Field("Params") String jsonRedimir);
    }
}

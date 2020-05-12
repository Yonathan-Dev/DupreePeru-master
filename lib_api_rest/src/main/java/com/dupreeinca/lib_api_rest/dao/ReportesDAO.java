package com.dupreeinca.lib_api_rest.dao;

import android.content.Context;

import com.dupreeinca.lib_api_rest.dao.base.TTCallback;
import com.dupreeinca.lib_api_rest.dao.base.TTGenericDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.request.Index;
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
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ReportesDAO extends TTGenericDAO {


    public ReportesDAO(Context context) {
        super(context);
    }

    public void getCDR(Identy data, final TTResultListener<ListCDR> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<ListCDR> call = userREST.getCDR(
                new Gson().toJson(data),
                new Gson().toJson(new Index("1"))
        );
        call.enqueue(new TTCallback<ListCDR>(new TTResultListener<ListCDR>() {
            @Override
            public void success(ListCDR result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getPQR(Identy data, final TTResultListener<ListPQR> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<ListPQR> call = userREST.getPQR(
                new Gson().toJson(data),
                new Gson().toJson(new Index("1"))
        );
        call.enqueue(new TTCallback<ListPQR>(new TTResultListener<ListPQR>() {
            @Override
            public void success(ListPQR result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getFacturasPDF(Identy data, final TTResultListener<ListFactura> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<ListFactura> call = userREST.getFacturas(
                new Gson().toJson(data)
        );
        call.enqueue(new TTCallback<ListFactura>(new TTResultListener<ListFactura>() {
            @Override
            public void success(ListFactura result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getPagos(Identy data, final TTResultListener<ListPagosRealizados> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<ListPagosRealizados> call = userREST.getPagos(
                new Gson().toJson(data)
        );
        call.enqueue(new TTCallback<ListPagosRealizados>(new TTResultListener<ListPagosRealizados>() {
            @Override
            public void success(ListPagosRealizados result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getCupoSaldoConf(Identy data, final TTResultListener<ListCupoSaldoConf> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<ListCupoSaldoConf> call = userREST.getCupoSaldoConf(
                new Gson().toJson(data)
        );
        call.enqueue(new TTCallback<ListCupoSaldoConf>(new TTResultListener<ListCupoSaldoConf>() {
            @Override
            public void success(ListCupoSaldoConf result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getPanelGrte(String token, final TTResultListener<ListPanelGte> listener){
        Rest rest = getRetrofit().create(Rest.class);
        Call<ListPanelGte> call = rest.getPanelGerente(new Gson().toJson(token));
        call.enqueue(new TTCallback<ListPanelGte>(new TTResultListener<ListPanelGte>() {
            @Override
            public void success(ListPanelGte result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getHistorialPedido(Identy data, final TTResultListener<HistorialDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<HistorialDTO> call = userREST.getHistorialPedido(new Gson().toJson(data));
        call.enqueue(new TTCallback<HistorialDTO>(new TTResultListener<HistorialDTO>() {
            @Override
            public void success(HistorialDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getPanelAsesora(final TTResultListener<PanelAsesoraDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<PanelAsesoraDTO> call = userREST.getPanelAsesora();
        call.enqueue(new TTCallback<PanelAsesoraDTO>(new TTResultListener<PanelAsesoraDTO>() {
            @Override
            public void success(PanelAsesoraDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getPuntosAsesora(Identy data, final TTResultListener<PuntosAsesoraDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<PuntosAsesoraDTO> call = userREST.getPuntosAsesora(new Gson().toJson(data));
        call.enqueue(new TTCallback<PuntosAsesoraDTO>(new TTResultListener<PuntosAsesoraDTO>() {
            @Override
            public void success(PuntosAsesoraDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getIncentivosReferido(Identy data, final TTResultListener<ReferidosDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<ReferidosDTO> call = userREST.getIncentivosReferido(new Gson().toJson(data), new Gson().toJson(new Index("1")));
        call.enqueue(new TTCallback<ReferidosDTO>(new TTResultListener<ReferidosDTO>() {
            @Override
            public void success(ReferidosDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void getPedRetenidos(Identy data, final TTResultListener<RetenidosDTO> listener){
        Rest userREST = getRetrofit().create(Rest.class);
        Call<RetenidosDTO> call = userREST.getPedRetenidos(new Gson().toJson(data));
        call.enqueue(new TTCallback<RetenidosDTO>(new TTResultListener<RetenidosDTO>() {
            @Override
            public void success(RetenidosDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        },getRetrofit()));
    }

    public void reporteUbicacionCliente(RequeridUbicacion data, final TTResultListener<GenericDTO> listener) {
        Rest userREST = getRetrofit().create(Rest.class);
        Call<GenericDTO> call = userREST.reporteUbicacionCliente(new Gson().toJson(data));
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

        @GET("reportes/cdr")
        Call<ListCDR> getCDR(
                @Query("filters") String jsonCedula, @Query("paginator") String pag
        );

        @GET("reportes/pqr")
        Call<ListPQR> getPQR(
                @Query("filters") String jsonCedula, @Query("paginator") String pag
        );

        @GET("reportes/pdf_factura")
        Call<ListFactura> getFacturas(@Query("filters") String jsonCedula);

        @GET("reportes/pagos")
        Call<ListPagosRealizados> getPagos(@Query("filters") String jsonCedula);

        @GET("reportes/cupo_saldo")
        Call<ListCupoSaldoConf> getCupoSaldoConf(@Query("filters") String jsonCedula);

        @GET("reportes/panel_nuevo")
        Call<ListPanelGte> getPanelGerente(@Query("filters") String jsonUsuario);

        @FormUrlEncoded
        @POST("reportes/factura")
        Call<HistorialDTO> getHistorialPedido(@Field("Params") String jsonCedula);

        @GET("reportes/panel_asesora")
        Call<PanelAsesoraDTO> getPanelAsesora();

        @GET("reportes/puntos")
        Call<PuntosAsesoraDTO> getPuntosAsesora(@Query("filters") String jsonCedula);

        @GET("reportes/referido")
        Call<ReferidosDTO> getIncentivosReferido(
                @Query("filters") String jsonCedula, @Query("paginator") String pag
        );

        @GET("reportes/pase_pedidos")
        Call<RetenidosDTO> getPedRetenidos(@Query("filters") String jsonCedula);

        @FormUrlEncoded
        @POST("panel/geo_clie")
        Call<GenericDTO> reporteUbicacionCliente(@Field("Params") String jsonUsuario);
    }
}

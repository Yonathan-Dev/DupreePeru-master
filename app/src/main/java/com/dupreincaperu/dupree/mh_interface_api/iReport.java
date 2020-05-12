package com.dupreincaperu.dupree.mh_interface_api;

import com.dupreeinca.lib_api_rest.model.dto.request.InscriptionDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.CatalogoPremiosList;
import com.dupreeinca.lib_api_rest.model.dto.response.BandejaDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCDR;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCampana;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCupoSaldoConf;
import com.dupreeinca.lib_api_rest.model.dto.response.EstadoPedidoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListFactura;
import com.dupreeinca.lib_api_rest.model.dto.response.FaltantesDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.LiquidarDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPreinscripcionDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPQR;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPagosRealizados;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPanelGte;
import com.dupreeinca.lib_api_rest.model.dto.response.PerfilDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ProductCatalogoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.PuntosAsesoraDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.RedimirDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ReferidosDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.RetenidosDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.DataAuth;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.PanelAsesoraDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.UrlsCatalogosDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by cloudemotion on 30/8/17.
 */

public interface iReport {
    @GET("reportes/campanas")
    Call<ListCampana> obtainCampanas();

    @GET("panel/catalogos")
    Call<UrlsCatalogosDTO> getUrlCatalogos();

    @GET("reportes/panel_gerente")
    Call<ListPanelGte> obtainPanelGrte(@Query("filters") String jsonCampana);

    @FormUrlEncoded
    @POST("reportes/datos_perfil")
    Call<PerfilDTO> getPerfilUser(@Field("Params") String jsonCedula);//el parametro filter no va, solo que
    //la libreria no permite parametros vacios en POST

    @FormUrlEncoded
    @POST("panel/modifica_perfil")
    Call<DataAuth> EditPerfil(@Field("Params") String jsonPerfil);

    @GET("reportes/pase_pedidos")
    Call<RetenidosDTO> obtainPedRetenidos(@Query("filters") String jsonCedula);

    @GET("reportes/cupo_saldo")
    Call<ListCupoSaldoConf> obtainCupoSaldoConf(@Query("filters") String jsonCedula);

    @GET("panel/lista_preinscripcion")
    Call<ListPreinscripcionDTO> obtainListPre(
            @Query("perfil") String perfil,
            @Query("index_pages") int index_pages,
            @Query("valor") String valor,
            @Query("token") String token
    );

    @FormUrlEncoded
    @POST("reportes/nombre")
    Call<GenericDTO> validateReferido(@Field("Params") String jsonValidaRef);

    @FormUrlEncoded
    @POST("reportes/cifin")
    Call<GenericDTO> validateCentralRiesgo(@Field("Params") String jsonValidaRef);

    @FormUrlEncoded
    @POST("panel/inscripcion")
    Call<GenericDTO> inscribir(@Field("Params") String jsonInscription);

    @FormUrlEncoded
    @POST("panel/actualiza_pre")
    Call<InscriptionDTO> editInscription(@Field("Params") String jsonCedula);

    @GET("reportes/panel_asesora")
    Call<PanelAsesoraDTO> obtainPanelAsesora();

    @GET("pedidos/faltante")
    Call<FaltantesDTO> obtainFaltantes();

    @GET("pedidos/premios")
    Call<RedimirDTO> obtainRedimirIncentivos(
            @Query("filters") String jsonCedula, @Query("paginator") String pag
    );

    @GET("reportes/puntos")
    Call<PuntosAsesoraDTO> obtainPuntosAsesora(@Query("filters") String jsonCedula);

    @FormUrlEncoded
    @POST("pedidos/redime_premios")
    Call<GenericDTO> redimirPremios(@Field("Params") String jsonRedimir);

    @GET("reportes/referido")
    Call<ReferidosDTO> obtainIncentivosReferido(
            @Query("filters") String jsonCedula, @Query("paginator") String pag
    );

    @GET("reportes/cdr")
    Call<ListCDR> obtainCDR(
            @Query("filters") String jsonCedula, @Query("paginator") String pag
    );

    @GET("reportes/pqr")
    Call<ListPQR> obtainPQR(
            @Query("filters") String jsonCedula, @Query("paginator") String pag
    );

    @GET("reportes/pdf_factura")
    Call<ListFactura> obtainFacturas(@Query("filters") String jsonCedula);

    @GET("reportes/pagos")
    Call<ListPagosRealizados> obtainPagos(@Query("filters") String jsonCedula);

    //////////////PEDIDO
    @GET("pedidos/estado")
    Call<EstadoPedidoDTO> obtainEstadoPedido(@Query("filters") String jsonCedula);

    @FormUrlEncoded
    @POST("pedidos/confirma")
    Call<GenericDTO> confirmarPedido(@Field("Params") String jsonConfimPed);

    @FormUrlEncoded
    @POST("pedidos/liquida")
    Call<LiquidarDTO> liquidarPedido(@Field("Params") String jsonLiquidate);

    @GET("pedidos/productos")
    Call<ProductCatalogoDTO> obtainProductos();

    @FormUrlEncoded
    @POST("panel/estado_prelider")
    Call<GenericDTO> aprobarPreinscripcion(@Field("Params") String jsonPerfil);

    //@FormUrlEncoded
    @POST("mensajes/consulta")
    Call<BandejaDTO> obtainMessages();

    @FormUrlEncoded
    @POST("mensajes/actualiza")
    Call<GenericDTO> readMessages(@Field("Params") String jsonUsuario);

    @FormUrlEncoded
    @POST("mensajes/borrar")
    Call<GenericDTO> deleteMessages(@Field("Params") String jsonUsuario);

    @GET("panel/folletos")
    Call<CatalogoPremiosList> obtainCatalogoPremios();
}

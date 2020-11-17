package com.dupreeinca.lib_api_rest.model.dto.request;

import java.util.List;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class LiquidarMensajeSend {


    private String nume_iden;
    private String codi_camp;
    private String codi_usua;
    private List<LiquidarMensajeProducto> productos;


    public LiquidarMensajeSend(String nume_iden, String codi_camp, String codi_usua, List<LiquidarMensajeProducto> productos) {
        this.nume_iden = nume_iden;
        this.codi_camp = codi_camp;
        this.codi_usua = codi_usua;
        this.productos = productos;
    }

    public void setNume_iden(String nume_iden) {
        this.nume_iden = nume_iden;
    }

    public void setCodi_camp(String codi_camp) {
        this.codi_camp = codi_camp;
    }

    public void setCodi_usua(String codi_usua) {
        this.codi_usua = codi_usua;
    }

    public void setProductos(List<LiquidarMensajeProducto> productos) {
        this.productos = productos;
    }

}

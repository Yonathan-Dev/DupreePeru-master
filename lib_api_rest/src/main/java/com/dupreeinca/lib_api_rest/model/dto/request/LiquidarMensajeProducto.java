package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class LiquidarMensajeProducto {
    private String codi_vent;
    private int cant_pedi;

    public LiquidarMensajeProducto(String codi_vent, int cant_pedi) {
        this.codi_vent = codi_vent;
        this.cant_pedi = cant_pedi;
    }


    public String getCodi_vent() {
        return codi_vent;
    }

    public int getCant_pedi() {
        return cant_pedi;
    }

}

package com.dupreeinca.lib_api_rest.model.dto.response;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

/**
 * Created by cloudemotion on 30/8/17.
 */

public class ListItemPanelGte {
    @SerializedName("table")
    private List<ItemPanelGte> panelGteDetails;
    // private List<ListItemPanelGte> panelGteDetails;
    private int campana;
    @SerializedName("cantidad_mensajes")
    private String cantidadMensajes;
    @SerializedName("fecha_corte")
    private String fechaCorte;
    @SerializedName("fecha_cierre")
    private String fechaCierre;

    public Collection<? extends ItemPanelGte> getPanelGteDetails() {
        return panelGteDetails;
    }

    public int getCampana() {
        return campana;
    }

    public String getCantidadMensajes() {
        return cantidadMensajes;
    }

    public void setCantidadMensajes(String cantidadMensajes) {
        this.cantidadMensajes = cantidadMensajes;
    }

    public String getFechaCorte() {
        return fechaCorte;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }
}

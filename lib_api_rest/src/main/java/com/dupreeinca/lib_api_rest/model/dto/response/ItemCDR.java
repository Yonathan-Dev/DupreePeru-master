package com.dupreeinca.lib_api_rest.model.dto.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cloudemotion on 21/8/17.
 */

public class ItemCDR {
    @SerializedName("title_1")
    private String campana;
    @SerializedName("title_2")
    private String fecha;
    @SerializedName("title_3")
    private String factura;
    @SerializedName("title_4")
    private String productos;
    @SerializedName("title_5")
    private String descripcion;
    @SerializedName("title_6")
    private String cantidad;
    @SerializedName("title_7")
    private String tReclamo;
    @SerializedName("title_8")
    private String procede;
    @SerializedName("title_9")
    private String observ;

    public ItemCDR(String campana, String fecha, String factura, String productos, String descripcion, String cantidad, String tReclamo, String procede, String observ) {
        this.campana = campana;
        this.fecha = fecha;
        this.factura = factura;
        this.productos = productos;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.tReclamo = tReclamo;
        this.procede = procede;
        this.observ = observ;
    }

    public String getCampana() {
        return campana;
    }

    public String getFecha() {
        return fecha;
    }

    public String getFactura() {
        return factura;
    }

    public String getProductos() {
        return productos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String gettReclamo() {
        return tReclamo;
    }

    public String getProcede() {
        return procede;
    }

    public String getObserv() {
        return observ;
    }
}

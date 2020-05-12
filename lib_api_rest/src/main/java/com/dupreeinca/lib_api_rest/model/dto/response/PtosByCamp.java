package com.dupreeinca.lib_api_rest.model.dto.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cloudemotion on 21/8/17.
 */

public class PtosByCamp {
    private String campana;
    @SerializedName("Puntos_Pedido")
    private String puntos_Pedido;
    @SerializedName("Total_Referidos")
    private String total_Referidos;
    @SerializedName("Puntos_Referido")
    private String puntos_Referido;
    @SerializedName("Total_Puntos")
    private String total_Puntos;
    @SerializedName("Perdidos_Devolucion")
    private String perdidos_Devolucion;
    @SerializedName("Perdidos_Cartera")
    private String perdidos_Cartera;
    @SerializedName("Perdidos_Inactividad")
    private String perdidos_Inactividad;
    @SerializedName("Total_Puntos_Perdidos")
    private String total_Puntos_Perdidos;
    @SerializedName("Puntos_Adicionales")
    private String puntos_Adicionales;
    @SerializedName("Puntos_Efectivos")
    private String puntos_Efectivos;
    @SerializedName("Puntos_Redimidos")
    private String puntos_Redimidos;
    @SerializedName("Estado_Pago")
    private String estado_Pago;

    public PtosByCamp(String campana, String puntos_Pedido, String total_Referidos, String puntos_Referido, String total_Puntos, String perdidos_Devolucion, String perdidos_Cartera, String perdidos_Inactividad, String total_Puntos_Perdidos, String puntos_Adicionales, String puntos_Efectivos, String puntos_Redimidos, String estado_Pago) {
        this.campana = campana;
        this.puntos_Pedido = puntos_Pedido;
        this.total_Referidos = total_Referidos;
        this.puntos_Referido = puntos_Referido;
        this.total_Puntos = total_Puntos;
        this.perdidos_Devolucion = perdidos_Devolucion;
        this.perdidos_Cartera = perdidos_Cartera;
        this.perdidos_Inactividad = perdidos_Inactividad;
        this.total_Puntos_Perdidos = total_Puntos_Perdidos;
        this.puntos_Adicionales = puntos_Adicionales;
        this.puntos_Efectivos = puntos_Efectivos;
        this.puntos_Redimidos = puntos_Redimidos;
        this.estado_Pago = estado_Pago;
    }

    public String getCampana() {
        return campana;
    }

    public String getPuntos_Pedido() {
        return puntos_Pedido;
    }

    public String getTotal_Referidos() {
        return total_Referidos;
    }

    public String getPuntos_Referido() {
        return puntos_Referido;
    }

    public String getTotal_Puntos() {
        return total_Puntos;
    }

    public String getPerdidos_Devolucion() {
        return perdidos_Devolucion;
    }

    public String getPerdidos_Cartera() {
        return perdidos_Cartera;
    }

    public String getPerdidos_Inactividad() {
        return perdidos_Inactividad;
    }

    public String getTotal_Puntos_Perdidos() {
        return total_Puntos_Perdidos;
    }

    public String getPuntos_Adicionales() {
        return puntos_Adicionales;
    }

    public String getPuntos_Efectivos() {
        return puntos_Efectivos;
    }

    public String getPuntos_Redimidos() {
        return puntos_Redimidos;
    }

    public String getEstado_Pago() {
        return estado_Pago;
    }
}

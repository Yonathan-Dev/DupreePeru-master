package com.dupreeinca.lib_api_rest.model.dto.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cloudemotion on 19/8/17.
 */

public class ItemRetenido {
    @SerializedName("title_1")
    private String area;
    @SerializedName("title_2")
    private String zona;
    @SerializedName("title_3")
    private String cedula;
    @SerializedName("title_4")
    private String name;
    @SerializedName("title_5")
    private String celular;
    @SerializedName("title_6")
    private String numPedido;
    @SerializedName("title_7")
    private String neto;
    @SerializedName("title_8")
    private String saldo;
    @SerializedName("title_9")
    private String cupo;
    @SerializedName("title_10")
    private String cartera;
    @SerializedName("title_11")
    private String r_cupo;
    @SerializedName("title_12")
    private String code;
    @SerializedName("title_13")
    private String montoMinimo;
    @SerializedName("title_14")
    private String bloqueo;
    @SerializedName("title_15")
    private String minimoPublico;
    @SerializedName("title_16")
    private String totalPublico;

    public ItemRetenido(String area, String zona, String cedula, String name, String celular, String numPedido, String neto, String saldo, String cupo, String cartera, String r_cupo, String code, String montoMinimo, String bloqueo, String minimoPublico, String totalPublico) {
        this.area = area;
        this.zona = zona;
        this.cedula = cedula;
        this.name = name;
        this.celular = celular;
        this.numPedido = numPedido;
        this.neto = neto;
        this.saldo = saldo;
        this.cupo = cupo;
        this.cartera = cartera;
        this.r_cupo = r_cupo;
        this.code = code;
        this.montoMinimo = montoMinimo;
        this.bloqueo = bloqueo;
        this.minimoPublico = minimoPublico;
        this.totalPublico = totalPublico;
    }

    public String getArea() {
        return area;
    }

    public String getZona() {
        return zona;
    }

    public String getCedula() {
        return cedula;
    }

    public String getName() {
        return name;
    }

    public String getCelular() {
        return celular;
    }

    public String getNumPedido() {
        return numPedido;
    }

    public String getNeto() {
        return neto;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getCupo() {
        return cupo;
    }

    public String getCartera() {
        return cartera;
    }

    public String getR_cupo() {
        return r_cupo;
    }

    public String getCode() {
        return code;
    }

    public String getMontoMinimo() {
        return montoMinimo;
    }

    public String getBloqueo() {
        return bloqueo;
    }

    public String getMinimoPublico() {
        return minimoPublico;
    }

    public String getTotalPublico() {
        return totalPublico;
    }
}

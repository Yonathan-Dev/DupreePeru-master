package com.dupreeinca.lib_api_rest.model.dto.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cloudemotion on 23/8/17.
 */

public class IncentivoRef {
    @SerializedName("title_1")
    private String campana;
    @SerializedName("title_2")
    private String cedula;
    @SerializedName("title_3")
    private String nombre;
    @SerializedName("title_4")
    private String saldo;
    @SerializedName("title_5")
    private String observaciones;


    public IncentivoRef(String campana, String cedula, String nombre, String saldo, String observaciones) {
        this.campana = campana;
        this.cedula = cedula;
        this.nombre = nombre;
        this.saldo = saldo;
        this.observaciones = observaciones;
    }

    public String getCampana() {
        return campana;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getObservaciones() {
        return observaciones;
    }
}

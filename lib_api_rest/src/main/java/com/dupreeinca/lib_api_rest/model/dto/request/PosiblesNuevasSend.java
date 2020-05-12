package com.dupreeinca.lib_api_rest.model.dto.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class PosiblesNuevasSend {

    private String tipo_perf;
    private String valo_perf;

    @SerializedName("detalle")
    private List<PosiblesNuevas> posiblesNuevas;

    public PosiblesNuevasSend(String tipo_perf, String valo_perf, List<PosiblesNuevas> posiblesNuevas) {
        this.tipo_perf = tipo_perf;
        this.valo_perf = valo_perf;
        this.posiblesNuevas = posiblesNuevas;
    }

    public String getTipo_perf() {
        return tipo_perf;
    }

    public void setTipo_perf(String tipo_perf) {
        this.tipo_perf = tipo_perf;
    }

    public String getValo_perf() {
        return valo_perf;
    }

    public void setValo_perf(String valo_perf) {
        this.valo_perf = valo_perf;
    }

    public List<PosiblesNuevas> getPosiblesNuevas() {
        return posiblesNuevas;
    }

    public void setPosiblesNuevas(List<PosiblesNuevas> posiblesNuevas) {
        this.posiblesNuevas = posiblesNuevas;
    }
}

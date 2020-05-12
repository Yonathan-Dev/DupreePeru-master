package com.dupreeinca.lib_api_rest.model.dto.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cloudemotion on 2/9/17.
 */

public class ResumePuntos {
    @SerializedName("Efectivos")
    private int efectivos;
    @SerializedName("Redimidos")
    private int redimidos;
    @SerializedName("Disponibles")
    private int disponibles;
    @SerializedName("Pendientes_Pago")
    private String pendientes_pago;
    private String asesora;

    public int getEfectivos() {
        return efectivos;
    }

    public int getRedimidos() {
        return redimidos;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public String getPendientes_Pago() {
        return pendientes_pago;
    }

    public String getAsesora() {
        return asesora;
    }
}

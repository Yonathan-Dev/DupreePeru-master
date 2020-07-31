package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by cloudemotion on 14/9/17.
 */


public class ElimPreIns {
    public static final String ELIMINAR = "ELI";
    public static final String CANCELAR = "CAN";

    private String cedula;
    private String estado;

    public ElimPreIns(String cedula, String estado) {
        this.cedula = cedula;
        this.estado = estado;
    }
}
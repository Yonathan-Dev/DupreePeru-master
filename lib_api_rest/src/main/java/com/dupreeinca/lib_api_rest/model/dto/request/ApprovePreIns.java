package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by cloudemotion on 14/9/17.
 */


public class ApprovePreIns {
    public static final String APROBAR = "APR";
    public static final String RECHAZAR = "ANL";

    private String cedula;
    private String estado;

    public ApprovePreIns(String cedula, String estado) {
        this.cedula = cedula;
        this.estado = estado;
    }
}
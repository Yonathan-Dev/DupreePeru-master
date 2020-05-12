package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by marwuinh@gmail.com on 3/6/19.
 */

public class IdentyName {
    private String cedula = "";
    private String nombre = "";

    public IdentyName(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }
}

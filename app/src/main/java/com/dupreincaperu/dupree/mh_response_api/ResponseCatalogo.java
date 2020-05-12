package com.dupreincaperu.dupree.mh_response_api;

/**
 * Created by cloudemotion on 14/8/17.
 */

public class ResponseCatalogo {
    private String codigo;
    private String nombre;

    public ResponseCatalogo(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}

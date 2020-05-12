package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 14/8/17.
 */

public class ItemCampana {
    private String codigo;
    private String nombre;

    public ItemCampana(String codigo, String nombre) {
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

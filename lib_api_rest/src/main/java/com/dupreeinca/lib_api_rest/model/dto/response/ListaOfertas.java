package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Oferta;

import java.util.List;

/**
 * Created by cloudemotion on 12/9/17.
 */

public class ListaOfertas {
    private List<Oferta> productos;
    private String activo;

    public List<Oferta> getProductos() {
        return productos;
    }

    public String getActivo() {
        return activo;
    }
}

package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.DetalleProductos;

import java.util.List;

/**
 * Created by cloudemotion on 3/9/17.
 */

public class ListaProductos {
    private List<DetalleProductos> productos;
    private String precio_total;

    public List<DetalleProductos> getProductos() {
        return productos;
    }

    public String getPrecio_total() {
        return precio_total;
    }
}

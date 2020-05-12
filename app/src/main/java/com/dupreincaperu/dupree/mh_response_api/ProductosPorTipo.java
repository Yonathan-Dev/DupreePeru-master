package com.dupreincaperu.dupree.mh_response_api;

import com.dupreeinca.lib_api_rest.model.dto.response.ListaProductos;

/**
 * Created by cloudemotion on 3/9/17.
 */

public class ProductosPorTipo {
    private int id_pedido;
    private ListaProductos paquetones;
    private ListaProductos productos;
    private ListaProductos ofertas;
    private int puntos;
    private int precio_total;
    private String estatus;
    private String asesora;

    public int getId_pedido() {
        return id_pedido;
    }

    public ListaProductos getPaquetones() {
        return paquetones;
    }

    public ListaProductos getProductos() {
        return productos;
    }

    public ListaProductos getOfertas() {
        return ofertas;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getPrecio_total() {
        return precio_total;
    }

    public String getEstatus() {
        return estatus;
    }

    public String getAsesora() {
        return asesora;
    }
}

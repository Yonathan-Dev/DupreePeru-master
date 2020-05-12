package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 12/9/17.
 */

public class ResultEdoPrePedido {
    private String id_pedido;
    private ListaProductos productos;
    private String precio_total;
    private String estado_pedido;
    private String asesora;
    private String campana;


    public String getId_pedido() {
        return id_pedido;
    }

    public ListaProductos getProductos() {
        return productos;
    }

    public String getPrecio_total() {
        return precio_total;
    }

    public String getEstado_pedido() {
        return estado_pedido;
    }

    public String getAsesora() {
        return asesora;
    }

    public String getCampana() {
        return campana;
    }
}

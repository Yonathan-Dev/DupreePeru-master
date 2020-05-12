package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class LiquidarProducto {
    private String id;
    private int cantidad;
    private String valor;

    public LiquidarProducto(String id, int cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public LiquidarProducto(String id, int cantidad,String valor) {
        this.id = id;
        this.cantidad = cantidad;
        this.valor    = valor;
    }

    public String getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getValor() {  return valor;    }
}

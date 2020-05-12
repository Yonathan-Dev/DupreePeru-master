package com.dupreeinca.lib_api_rest.model.dto.request;

import java.util.List;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class LiquidarSend {

    private String id_pedido;
    private List<String> paquetones;
    private List<LiquidarProducto> productos;
    private List<LiquidarProducto> ofertas;

    public LiquidarSend(String id_pedido, List<String> paquetones, List<LiquidarProducto> productos, List<LiquidarProducto> ofertas) {
        this.id_pedido = id_pedido;
        this.paquetones = paquetones;
        this.productos = productos;
        this.ofertas = ofertas;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public void setPaquetones(List<String> paquetones) {
        this.paquetones = paquetones;
    }

    public void setProductos(List<LiquidarProducto> productos) {
        this.productos = productos;
    }

    public void setOfertas(List<LiquidarProducto> ofertas) {
        this.ofertas = ofertas;
    }
}

package com.dupreeinca.lib_api_rest.model.dto.request;

import java.util.List;

public class  PrePedidoSend{

    private String id_pedido;
    private String  campana;
    private List<LiquidarProducto> productos;

   //Constructor

    public PrePedidoSend(String id_pedido,String campana,List<LiquidarProducto> productos){
        this.id_pedido = id_pedido;
        this.campana   = campana;
        this.productos = productos;
    }

    public void setProductos(List<LiquidarProducto> productos) {
        this.productos = productos;
    }

    public void setCampana(String campana) { this.campana = campana;  }

    public void setId_pedido(String id_pedido) {        this.id_pedido = id_pedido;    }
}
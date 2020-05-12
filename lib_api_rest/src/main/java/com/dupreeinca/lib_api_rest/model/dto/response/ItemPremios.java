package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 14/8/17.
 */

public class ItemPremios {
    private String codigo;
    private String nombre;
    private String imagen;
    private String descripcion;
    private String puntos;
    private String cantidad;
    private boolean isSelected;
    private boolean isEnable;

    private boolean inTheCart=false;//para saber si ya esta en el carrito

    public ItemPremios(String codigo, String nombre, String imagen, String descripcion, String puntos, String cantidad, boolean isSelected, boolean isEnable) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.puntos = puntos;
        this.cantidad = cantidad;
        this.isSelected = isSelected;
        this.isEnable = isEnable;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPuntos() {
        return puntos;
    }

    public String getCantidad() {
        return cantidad;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public boolean isInTheCart() {
        return inTheCart;
    }

    public void setInTheCart(boolean inTheCart) {
        this.inTheCart = inTheCart;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}

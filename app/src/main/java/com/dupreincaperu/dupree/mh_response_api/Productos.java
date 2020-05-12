package com.dupreincaperu.dupree.mh_response_api;

/**
 * Created by cloudemotion on 16/8/17.
 */

public class Productos {
    private String id;
    private String name;
    private String valor;
    private String url_img;
    private String page;

    //no viene en el API
    private int cantidad;
    private boolean inTheCart=false;//para saber si ya esta en el carrito

    public Productos(String id, String name, String valor, String url_img, String page, int cantidad) {
        this.id = id;
        this.name = name;
        this.valor = valor;
        this.url_img = url_img;
        this.page = page;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValor() {
        return valor;
    }

    public String getUrl_img() {
        return url_img;
    }

    public String getPage() {
        return page;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isInTheCart() {
        return inTheCart;
    }

    public void setInTheCart(boolean inTheCart) {
        this.inTheCart = inTheCart;
    }
}

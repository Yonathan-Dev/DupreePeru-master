package com.dupreincaperu.dupree.mh_response_api;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class CatalogoList{
    private String id;//se usa en la db
    private String name;
    private String valor;
    private String url_img;
    private String page;

    private int cantidad=0;

    public CatalogoList(String id, String name, String valor, String url_img, String page, int cantidad) {
        this.id = id;
        this.name = name;
        this.valor = valor;
        this.url_img = url_img;
        this.page = page;
        this.cantidad = cantidad;
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
}

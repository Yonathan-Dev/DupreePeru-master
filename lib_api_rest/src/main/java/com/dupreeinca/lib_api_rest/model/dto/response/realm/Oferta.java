package com.dupreeinca.lib_api_rest.model.dto.response.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by marwuinh@gmail.com on 4/9/17.
 */

public class Oferta extends RealmObject{
    @PrimaryKey
    private String id;
    private String name;
    private String valor;
    private String valor_descuento;
    private String url_img;
    private String page;
    private int cantidad;
    //control

    //para control de ediciones de lo que local
    //esta en base de datos y lo que esta en server
    private int cantidadServer=0;//indica cuanto se envio al server
    private long time=0;//para control de fecha de item, agregado (o usar incremento)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValor_descuento() {
        return valor_descuento;
    }

    public void setValor_descuento(String valor_descuento) {
        this.valor_descuento = valor_descuento;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadServer() {
        return cantidadServer;
    }

    public void setCantidadServer(int cantidadServer) {
        this.cantidadServer = cantidadServer;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

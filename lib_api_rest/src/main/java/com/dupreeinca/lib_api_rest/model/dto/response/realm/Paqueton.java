package com.dupreeinca.lib_api_rest.model.dto.response.realm;

import io.realm.RealmObject;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class Paqueton extends RealmObject{
    private String id;
    private int id_cat;
    private String name;
    private String valor;
    private String valor_descuent;
    private String url_img;
    private String page;

    public String getId() {
        return id;
    }

    public int getId_cat() {
        return id_cat;
    }

    public String getName() {
        return name;
    }

    public String getValor() {
        return valor;
    }

    public String getValor_descuent() {
        return valor_descuent;
    }

    public String getUrl_img() {
        return url_img;
    }

    public String getPage() {
        return page;
    }
}

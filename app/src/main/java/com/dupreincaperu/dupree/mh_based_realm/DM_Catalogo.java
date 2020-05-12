package com.dupreincaperu.dupree.mh_based_realm;

import io.realm.RealmObject;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class DM_Catalogo extends RealmObject {
    private String id;//se usa en la db
    private String name;
    private String valor;
    private String url_img;
    private String page;

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
}

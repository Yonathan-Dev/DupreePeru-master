package com.dupreincaperu.dupree.mh_based_realm;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Catalogo;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by cloudemotion on 5/9/17.
 */

public class DM_List_Ofertas extends RealmObject{
    public static final int TYPE_PRODUCTOS=0;
    public static final int TYPE_OFERTAS=0;
    public static final int TYPE_PAQUETONES=0;

    RealmList<Catalogo> catalogoList;
    int type;

    public RealmList<Catalogo> getCatalogoList() {
        return catalogoList;
    }

    public void setCatalogoList(RealmList<Catalogo> catalogoList) {
        this.catalogoList = catalogoList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

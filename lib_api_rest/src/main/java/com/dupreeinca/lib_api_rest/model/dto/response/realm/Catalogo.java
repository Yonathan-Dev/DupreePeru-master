package com.dupreeinca.lib_api_rest.model.dto.response.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class Catalogo extends RealmObject{
    @PrimaryKey
    private String id;//se usa en la db
    private String name;
    private String valor;
    private String url_img;
    private String page;

    private int cantidad=0;

    //para control de ediciones de lo que local
    //esta en base de datos y lo que esta en server
    private int cantidadServer=0;//indica cuanto se envio al server
    private long time=0;//para control de fecha de item, agregado (o usar incremento)

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

    /**
     * para evitar conflictos de realm con retrofit
     * new Gson().toJson(realm.copyFromRealm(managedModel));
     * @param realm
     * @return
     */
    public Catalogo toUnmanaged(Realm realm) {
        return isManaged() ? realm.copyFromRealm(this) : this;
    }
}

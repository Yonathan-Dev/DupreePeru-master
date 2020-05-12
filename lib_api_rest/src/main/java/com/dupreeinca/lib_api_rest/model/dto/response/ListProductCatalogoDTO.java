package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Catalogo;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Oferta;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class ListProductCatalogoDTO {
    private List<Catalogo> productos;
    private PaquetonesByCategoryDTO paquetones;
    @SerializedName("Ofertas")
    private List<Oferta> ofertas;
    private int estado_paqueton;
    private int estado_ofertas;

    public List<Catalogo> getProductos() {
        return productos;
    }

    public PaquetonesByCategoryDTO getPaquetones() {
        return paquetones;
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public int getEstado_paqueton() {
        return estado_paqueton;
    }

    public int getEstado_ofertas() {
        return estado_ofertas;
    }
}

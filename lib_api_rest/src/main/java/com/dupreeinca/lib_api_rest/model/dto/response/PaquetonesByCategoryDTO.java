package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Paqueton;

import java.util.List;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class PaquetonesByCategoryDTO {
    private List<Paqueton> linea_1;
    private List<Paqueton> linea_2;
    private List<Paqueton> linea_3;
    private List<Paqueton> linea_4;
    private int paquetones_valor;

    public List<Paqueton> getLinea_1() {
        return linea_1;
    }

    public List<Paqueton> getLinea_2() {
        return linea_2;
    }

    public List<Paqueton> getLinea_3() {
        return linea_3;
    }

    public List<Paqueton> getLinea_4() {
        return linea_4;
    }

    public int getPaquetones_valor() {
        return paquetones_valor;
    }
}

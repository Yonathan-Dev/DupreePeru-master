package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemPremios;

import java.util.List;

/**
 * Created by cloudemotion on 1/9/17.
 */

public class ListPremios {
    private List<ItemPremios> premios;
    private int puntos_premio;

    public List<ItemPremios> getPremios() {
        return premios;
    }

    public int getPuntos_premio() {
        return puntos_premio;
    }

    public void setPuntos_premio(int puntos_premio) {
        this.puntos_premio = puntos_premio;
    }
}

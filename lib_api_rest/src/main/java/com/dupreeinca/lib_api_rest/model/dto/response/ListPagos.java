package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemPagosrealizados;

import java.util.List;

/**
 * Created by cloudemotion on 2/9/17.
 */

public class ListPagos {
    private List<ItemPagosrealizados> pago;
    private String asesora;

    public List<ItemPagosrealizados> getPago() {
        return pago;
    }

    public String getAsesora() {
        return asesora;
    }
}

package com.dupreeinca.lib_api_rest.model.dto.response;

import java.util.List;

/**
 * Created by cloudemotion on 25/8/17.
 */

public class DepartamentoDTO {
    private String id_dpto;
    private String name_dpto;
    private List<CiudadDTO> ciudades;

    public String getId_dpto() {
        return id_dpto;
    }

    public String getName_dpto() {
        return name_dpto;
    }

    public List<CiudadDTO> getCiudades() {
        return ciudades;
    }
}
